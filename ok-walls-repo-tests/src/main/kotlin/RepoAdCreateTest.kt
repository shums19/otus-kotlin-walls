package ru.otus.kotlin.walls.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.kotlin.walls.common.models.*
import ru.otus.kotlin.walls.common.repo.DbAdRequest
import ru.otus.kotlin.walls.common.repo.IAdRepository
import ru.otus.kotlin.walls.stubs.AdStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdCreateTest {
    abstract val repo: IAdRepository

    protected open val lockNew: AdLock = AdLock("20000000-0000-0000-0000-000000000002")

    private val createObj = AdStub.prepareResult {
        id = AdId.NONE
        title = AdTitle("create object")
        description = AdDescription("create object description")
        ownerId = UserId("owner-123")
    }

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createAd(DbAdRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: AdId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.title, result.data?.title)
        assertEquals(expected.description, result.data?.description)
        assertEquals(expected.ownerId, result.data?.ownerId)
        assertEquals(expected.isActive, result.data?.isActive)
        assertEquals(expected.hasLift, result.data?.hasLift)
        assertEquals(expected.price, result.data?.price)
        assertEquals(expected.area, result.data?.area)
        assertEquals(expected.floor, result.data?.floor)
        assertEquals(expected.roomsNumber, result.data?.roomsNumber)
        assertEquals(expected.type, result.data?.type)
        assertEquals(expected.status, result.data?.status)
        assertEquals(expected.buildingType, result.data?.buildingType)
        assertNotEquals(AdId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitAds("create") {
        override val initObjects: List<Ad> = emptyList()
    }
}
