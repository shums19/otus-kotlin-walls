package ru.otus.kotlin.walls.repo.cassandra

import com.benasher44.uuid.uuid4
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import com.datastax.oss.driver.internal.core.type.codec.extras.enums.EnumNameCodec
import com.datastax.oss.driver.internal.core.type.codec.registry.DefaultCodecRegistry
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.slf4j.LoggerFactory
import ru.otus.kotlin.walls.repo.cassandra.model.AdCassandraDTO
import ru.otus.kotlin.walls.common.helpers.asAdError
import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdId
import ru.otus.kotlin.walls.common.models.AdLock
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.repo.*
import ru.otus.kotlin.walls.repo.cassandra.model.RepoAdBuildingType
import ru.otus.kotlin.walls.repo.cassandra.model.RepoAdRealEstateStatus
import ru.otus.kotlin.walls.repo.cassandra.model.RepoAdRealEstateType
import java.net.InetAddress
import java.net.InetSocketAddress
import java.util.concurrent.CompletionStage
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class AdRepoCassandra(
    private val keyspaceName: String,
    private val host: String = "localhost",
    private val port: Int = 9042,
    private val user: String = "cassandra",
    private val pass: String = "cassandra",
    private val testing: Boolean = false,
    private val timeout: Duration = 30.toDuration(DurationUnit.SECONDS),
    private val randomUuid: () -> String = { uuid4().toString() },
    initObjects: Collection<Ad> = emptyList(),
) : IAdRepository {
    private val log = LoggerFactory.getLogger(javaClass)

    private val codecRegistry by lazy {
        DefaultCodecRegistry("default").apply {
            register(EnumNameCodec(RepoAdRealEstateType::class.java))
            register(EnumNameCodec(RepoAdRealEstateStatus::class.java))
            register(EnumNameCodec(RepoAdBuildingType::class.java))
        }
    }

    private val session by lazy {
        CqlSession.builder()
            .addContactPoints(parseAddresses(host, port))
            .withLocalDatacenter("datacenter1")
            .withAuthCredentials(user, pass)
            .withCodecRegistry(codecRegistry)
            .build()
    }

    private val mapper by lazy { CassandraMapper.builder(session).build() }

    private fun createSchema(keyspace: String) {
        session.execute(
            SchemaBuilder
                .createKeyspace(keyspace)
                .ifNotExists()
                .withSimpleStrategy(1)
                .build()
        )
        session.execute(AdCassandraDTO.table(keyspace, AdCassandraDTO.TABLE_NAME))
        session.execute(AdCassandraDTO.titleIndex(keyspace, AdCassandraDTO.TABLE_NAME))
    }

    private val dao by lazy {
        if (testing) {
            createSchema(keyspaceName)
        }
        mapper.adDao(keyspaceName, AdCassandraDTO.TABLE_NAME).apply {
            runBlocking {
                initObjects.map { model ->
                    withTimeout(timeout) {
                        create(AdCassandraDTO(model)).await()
                    }
                }
            }
        }
    }

    private fun errorToAdResponse(e: Exception) = DbAdResponse.error(e.asAdError())
    private fun errorToAdsResponse(e: Exception) = DbAdsResponse.error(e.asAdError())

    private suspend inline fun <DbRes, Response> doDbAction(
        name: String,
        crossinline daoAction: () -> CompletionStage<DbRes>,
        okToResponse: (DbRes) -> Response,
        errorToResponse: (Exception) -> Response
    ): Response = doDbAction(
        name,
        {
            val dbRes = withTimeout(timeout) { daoAction().await() }
            okToResponse(dbRes)
        },
        errorToResponse
    )

    private suspend inline fun readAndDoDbAction(
        name: String,
        id: AdId,
        successResult: Ad?,
        daoAction: () -> CompletionStage<Boolean>,
        errorToResponse: (Exception) -> DbAdResponse
    ): DbAdResponse =
        if (id == AdId.NONE)
            ID_IS_EMPTY
        else doDbAction(
            name,
            {
                val read = dao.read(id.value).await()
                if (read == null) ID_NOT_FOUND
                else {
                    val success = daoAction().await()
                    if (success) DbAdResponse.success(successResult ?: read.toAdModel())
                    else DbAdResponse(
                        read.toAdModel(),
                        false,
                        CONCURRENT_MODIFICATION.errors
                    )
                }
            },
            errorToResponse
        )

    private inline fun <Response> doDbAction(
        name: String,
        daoAction: () -> Response,
        errorToResponse: (Exception) -> Response
    ): Response =
        try {
            daoAction()
        } catch (e: Exception) {
            log.error("Failed to $name", e)
            errorToResponse(e)
        }

    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        val new = rq.ad.copy(id = AdId(randomUuid()), lock = AdLock(randomUuid()))
        return doDbAction(
            "create",
            { dao.create(AdCassandraDTO(new)) },
            { DbAdResponse.success(new) },
            ::errorToAdResponse
        )
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse =
        if (rq.id == AdId.NONE)
            ID_IS_EMPTY
        else doDbAction(
            "read",
            { dao.read(rq.id.value) },
            { found ->
                if (found != null) DbAdResponse.success(found.toAdModel())
                else ID_NOT_FOUND
            },
            ::errorToAdResponse
        )

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        val prevLock = rq.ad.lock.value
        val new = rq.ad.copy(lock = AdLock(randomUuid()))
        val dto = AdCassandraDTO(new)

        return readAndDoDbAction(
            "update",
            rq.ad.id,
            new,
            { dao.update(dto, prevLock) },
            ::errorToAdResponse
        )
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse =
        readAndDoDbAction(
            "delete",
            rq.id,
            null,
            { dao.delete(rq.id.value, rq.lock.value) },
            ::errorToAdResponse
        )


    override suspend fun searchAd(rq: DbAdSearchRequest): DbAdsResponse =
        doDbAction(
            "search",
            { dao.search(rq) },
            { found ->
                DbAdsResponse.success(found.map { it.toAdModel() })
            },
            ::errorToAdsResponse
        )

    companion object {
        private val ID_IS_EMPTY = DbAdResponse.error(AdError(field = "id", message = "Id is empty"))
        private val ID_NOT_FOUND =
            DbAdResponse.error(AdError(field = "id", code = "not-found", message = "Not Found"))
        private val CONCURRENT_MODIFICATION =
            DbAdResponse.error(AdError(field = "lock", code = "concurrency", message = "Concurrent modification"))
    }
}

private fun parseAddresses(hosts: String, port: Int): Collection<InetSocketAddress> = hosts
    .split(Regex("""\s*,\s*"""))
    .map { InetSocketAddress(InetAddress.getByName(it), port) }
