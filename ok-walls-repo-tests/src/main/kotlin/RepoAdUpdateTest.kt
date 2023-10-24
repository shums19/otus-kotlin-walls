package ru.otus.kotlin.walls.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.kotlin.walls.common.models.*
import ru.otus.kotlin.walls.common.repo.DbAdRequest
import ru.otus.kotlin.walls.common.repo.IAdRepository
import ru.otus.kotlin.walls.stubs.AdStub
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdUpdateTest {
    abstract val repo: IAdRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = AdId("ad-repo-update-not-found")
    protected val lockBad = AdLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = AdLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        AdStub.prepareResult {
            id = updateSucc.id
            title = AdTitle("update object")
            description = AdDescription("update object description")
            ownerId = UserId("owner-123")
            lock = initObjects.first().lock
        }
    }
    private val reqUpdateNotFound = AdStub.prepareResult {
        id = updateIdNotFound
        title = AdTitle("update object not found")
        description = AdDescription("update object not found description")
        ownerId = UserId("owner-123")
        lock = initObjects.first().lock
    }
    private val reqUpdateConc by lazy {
        AdStub.prepareResult {
            id = updateConc.id
            title = AdTitle("update object not found")
            description = AdDescription("update object not found description")
            ownerId = UserId("owner-123")
            lock = lockBad
        }
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.title, result.data?.title)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(reqUpdateSucc.ownerId, result.data?.ownerId)
        assertEquals(reqUpdateSucc.isActive, result.data?.isActive)
        assertEquals(reqUpdateSucc.hasLift, result.data?.hasLift)
        assertEquals(reqUpdateSucc.price, result.data?.price)
        assertEquals(reqUpdateSucc.area, result.data?.area)
        assertEquals(reqUpdateSucc.floor, result.data?.floor)
        assertEquals(reqUpdateSucc.roomsNumber, result.data?.roomsNumber)
        assertEquals(reqUpdateSucc.type, result.data?.type)
        assertEquals(reqUpdateSucc.status, result.data?.status)
        assertEquals(reqUpdateSucc.buildingType, result.data?.buildingType)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitAds("update") {
        override val initObjects: List<Ad> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
