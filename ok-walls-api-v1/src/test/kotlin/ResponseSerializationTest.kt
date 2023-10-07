package ru.otus.kotlin.walls.api.v1

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import ru.otus.kotlin.walls.api.v1.models.AdCreateResponse
import ru.otus.kotlin.walls.api.v1.models.AdResponseObject
import ru.otus.kotlin.walls.api.v1.models.BuildingType
import ru.otus.kotlin.walls.api.v1.models.IResponse
import ru.otus.kotlin.walls.api.v1.models.Status
import ru.otus.kotlin.walls.api.v1.models.Type

class ResponseSerializationTest : FreeSpec({
    val response = AdCreateResponse(
        requestId = "123",
        ad = AdResponseObject(
            id = "5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a",
            ownerId = "51f51c82-049d-45b7-9e41-68fc0121c90f",
            title = "title",
            description = "description",
            active = true,
            type = Type.APARTMENT,
            status = Status.NEW,
            area = 52.5.toBigDecimal(),
            price = 3520000.toBigDecimal(),
            buildingType = BuildingType.BRICK,
            hasLift = true,
            roomsNumber = 3,
            floor = 4,
        )
    )
    val responseJson = """
        {
            "responseType": "create",
            "responseType": null,
            "requestId": "123",
            "result": null,
            "errors": null,
            "ad": {
                "title": "title",
                "description": "description",
                "active": true,
                "type": "APARTMENT",
                "status": "NEW",
                "area": 52.5,
                "price": 3520000,
                "buildingType": "BRICK",
                "hasLift": true,
                "roomsNumber": 3,
                "floor": 4,
                "id": "5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a",
                "ownerId": "51f51c82-049d-45b7-9e41-68fc0121c90f",
                "lock": null,
                "permissions": null
            }
        }
    """.trimIndent()

    "serialize" {
        val json = apiV1Mapper.writeValueAsString(response)

        json shouldEqualJson responseJson
    }

    "deserialize" {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as AdCreateResponse

        obj shouldBe response
    }

    "naked deserialize" {
        val obj = apiV1Mapper.readValue(responseJson, AdCreateResponse::class.java)

        obj shouldBe response
    }
})
