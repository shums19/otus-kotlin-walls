package ru.otus.kotlin.walls.stubs

import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdArea
import ru.otus.kotlin.walls.common.models.AdBuildingType
import ru.otus.kotlin.walls.common.models.AdDescription
import ru.otus.kotlin.walls.common.models.AdFloor
import ru.otus.kotlin.walls.common.models.AdId
import ru.otus.kotlin.walls.common.models.AdPermissionClient
import ru.otus.kotlin.walls.common.models.AdPrice
import ru.otus.kotlin.walls.common.models.AdRealEstateStatus
import ru.otus.kotlin.walls.common.models.AdRealEstateType
import ru.otus.kotlin.walls.common.models.AdRoomsNumber
import ru.otus.kotlin.walls.common.models.AdTitle
import ru.otus.kotlin.walls.common.models.UserId

object AdStubApatment {
    val AD_APARTMENT: Ad
        get() = Ad(
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
            permissionsClient = mutableSetOf(
                AdPermissionClient.READ,
                AdPermissionClient.UPDATE,
                AdPermissionClient.DELETE,
                AdPermissionClient.MAKE_VISIBLE_PUBLIC,
                AdPermissionClient.MAKE_VISIBLE_GROUP,
                AdPermissionClient.MAKE_VISIBLE_OWNER,
            ),
        )
}
