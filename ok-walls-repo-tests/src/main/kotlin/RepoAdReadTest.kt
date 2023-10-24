package ru.otus.kotlin.walls.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdId
import ru.otus.kotlin.walls.common.repo.DbAdIdRequest
import ru.otus.kotlin.walls.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdReadTest {
    abstract val repo: IAdRepository
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readAd(DbAdIdRequest(readSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(readSucc, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readAd(DbAdIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitAds("delete") {
        override val initObjects: List<Ad> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = AdId("ad-repo-read-notFound")

    }
}
