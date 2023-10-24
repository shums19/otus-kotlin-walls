package ru.otus.kotlin.walls.stubs

import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdArea
import ru.otus.kotlin.walls.common.models.AdBuildingType
import ru.otus.kotlin.walls.common.models.AdDescription
import ru.otus.kotlin.walls.common.models.AdFloor
import ru.otus.kotlin.walls.common.models.AdId
import ru.otus.kotlin.walls.common.models.AdLock
import ru.otus.kotlin.walls.common.models.AdPermissionClient
import ru.otus.kotlin.walls.common.models.AdPrice
import ru.otus.kotlin.walls.common.models.AdRealEstateStatus
import ru.otus.kotlin.walls.common.models.AdRealEstateType
import ru.otus.kotlin.walls.common.models.AdRoomsNumber
import ru.otus.kotlin.walls.common.models.AdTitle
import ru.otus.kotlin.walls.common.models.UserId

object AdStub {
    fun get(): Ad = Ad(
        id = AdId("5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a"),
        title = AdTitle("3-комнатная квартира"),
        description = AdDescription("Квартира в центре"),
        ownerId = UserId("51f51c82-049d-45b7-9e41-68fc0121c90f"),
        isActive = true,
        hasLift = true,
        type = AdRealEstateType.APARTMENT,
        status = AdRealEstateStatus.NEW,
        area = AdArea(60.5.toBigDecimal()),
        price = AdPrice(3000000.toBigDecimal()),
        roomsNumber = AdRoomsNumber(3),
        floor = AdFloor(4),
        buildingType = AdBuildingType.BRICK,
        lock = AdLock("e08d0ed8-7270-11ee-b962-0242ac120002"),
        permissionsClient = mutableSetOf(
            AdPermissionClient.READ,
            AdPermissionClient.UPDATE,
            AdPermissionClient.DELETE,
            AdPermissionClient.MAKE_VISIBLE_PUBLIC,
            AdPermissionClient.MAKE_VISIBLE_GROUP,
            AdPermissionClient.MAKE_VISIBLE_OWNER,
        ),
    )

    fun prepareResult(block: Ad.() -> Unit): Ad = get().apply(block)

    private fun adForSearch(id: String, filter: String) = prepareResult {
        this.id = AdId(id)
        this.title = AdTitle("$filter $id")
        this.description = AdDescription("desc $filter $id")
    }

    fun prepareSearchList(filter: String) = listOf(
        adForSearch(id = "5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a", filter = filter),
        adForSearch(id = "51f51c82-049d-45b7-9e41-68fc0121c90f", filter = filter),
        adForSearch(id = "8b32d0fe-6527-11ee-8c99-0242ac120002", filter = filter),
        adForSearch(id = "96cd781a-6527-11ee-8c99-0242ac120002", filter = filter),
        adForSearch(id = "276004ea-b726-4a9b-bec7-37b8bb0852e6", filter = filter),
    )
}
