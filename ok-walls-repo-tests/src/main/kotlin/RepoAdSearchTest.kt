package ru.otus.kotlin.walls.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.UserId
import ru.otus.kotlin.walls.common.repo.DbAdSearchRequest
import ru.otus.kotlin.walls.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdSearchTest {
    abstract val repo: IAdRepository

    protected open val initializedObjects: List<Ad> = initObjects

    @Test
    fun searchBySearchString() = runRepoTest {
        val result = repo.searchAd(DbAdSearchRequest(searchString = "search"))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.value }
        assertEquals(expected, result.data?.sortedBy { it.id.value })
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitAds("search") {

        val searchOwnerId = UserId("owner-124")
        override val initObjects: List<Ad> = listOf(
            createInitTestModel("ad1"),
            createInitTestModel("ad2 search", ownerId = searchOwnerId),
            createInitTestModel("ad3"),
            createInitTestModel("ad4 search", ownerId = searchOwnerId),
            createInitTestModel("ad5"),
        )
    }
}
