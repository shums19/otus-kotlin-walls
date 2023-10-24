package ru.otus.kotlin.walls.repo.cassandra

import com.datastax.oss.driver.api.core.cql.AsyncResultSet
import com.datastax.oss.driver.api.mapper.MapperContext
import com.datastax.oss.driver.api.mapper.entity.EntityHelper
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import ru.otus.kotlin.walls.repo.cassandra.model.AdCassandraDTO
import ru.otus.kotlin.walls.common.repo.DbAdSearchRequest
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.function.BiConsumer

class AdCassandraSearchProvider(
    private val context: MapperContext,
    private val entityHelper: EntityHelper<AdCassandraDTO>
) {
    fun search(filter: DbAdSearchRequest): CompletionStage<Collection<AdCassandraDTO>> {
        var select = entityHelper.selectStart().allowFiltering()

        if (filter.searchString.isNotBlank()) {
            select = select
                .whereColumn(AdCassandraDTO.COLUMN_TITLE)
                .like(QueryBuilder.literal("%${filter.searchString}%"))
        }

        val asyncFetcher = AsyncFetcher()

        context.session
            .executeAsync(select.build())
            .whenComplete(asyncFetcher)

        return asyncFetcher.stage
    }

    inner class AsyncFetcher : BiConsumer<AsyncResultSet?, Throwable?> {
        private val buffer = mutableListOf<AdCassandraDTO>()
        private val future = CompletableFuture<Collection<AdCassandraDTO>>()
        val stage: CompletionStage<Collection<AdCassandraDTO>> = future

        override fun accept(resultSet: AsyncResultSet?, t: Throwable?) {
            when {
                t != null -> future.completeExceptionally(t)
                resultSet == null -> future.completeExceptionally(IllegalStateException("ResultSet should not be null"))
                else -> {
                    buffer.addAll(resultSet.currentPage().map { entityHelper.get(it, false) })
                    if (resultSet.hasMorePages())
                        resultSet.fetchNextPage().whenComplete(this)
                    else
                        future.complete(buffer)
                }
            }
        }
    }
}
