package ru.otus.kotlin.walls.repo.cassandra

import org.testcontainers.containers.CassandraContainer
import ru.otus.kotlin.walls.repo.tests.*
import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdLock
import ru.otus.kotlin.walls.common.repo.IAdRepository
import java.time.Duration

class RepoAdCassandraCreateTest : RepoAdCreateTest() {
    override val repo: IAdRepository = TestCompanion.repository(initObjects, "ks_create", lockNew)
}

class RepoAdCassandraDeleteTest : RepoAdDeleteTest() {
    override val repo: IAdRepository = TestCompanion.repository(initObjects, "ks_delete", lockOld)
}

class RepoAdCassandraReadTest : RepoAdReadTest() {
    override val repo: IAdRepository = TestCompanion.repository(initObjects, "ks_read", AdLock(""))
}

class RepoAdCassandraSearchTest : RepoAdSearchTest() {
    override val repo: IAdRepository = TestCompanion.repository(initObjects, "ks_search", AdLock(""))
}

class RepoAdCassandraUpdateTest : RepoAdUpdateTest() {
    override val repo: IAdRepository = TestCompanion.repository(initObjects, "ks_update", lockNew)
}

class TestCasandraContainer : CassandraContainer<TestCasandraContainer>("cassandra:3.11.2")

object TestCompanion {
    private val container by lazy {
        TestCasandraContainer().withStartupTimeout(Duration.ofSeconds(300L))
            .also { it.start() }
    }

    fun repository(initObjects: List<Ad>, keyspace: String, lock: AdLock): AdRepoCassandra {
        return AdRepoCassandra(
            keyspaceName = keyspace,
            host = container.host,
            port = container.getMappedPort(CassandraContainer.CQL_PORT),
            testing = true, randomUuid = { lock.value }, initObjects = initObjects
        )
    }
}
