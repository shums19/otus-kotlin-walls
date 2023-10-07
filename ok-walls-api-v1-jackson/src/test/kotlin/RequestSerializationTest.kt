package ru.otus.kotlin.walls.api.v1

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import ru.otus.kotlin.walls.api.v1.models.AdCreateObject
import ru.otus.kotlin.walls.api.v1.models.AdCreateRequest
import ru.otus.kotlin.walls.api.v1.models.AdDebug
import ru.otus.kotlin.walls.api.v1.models.AdRequestDebugMode
import ru.otus.kotlin.walls.api.v1.models.AdRequestDebugStub
import ru.otus.kotlin.walls.api.v1.models.BuildingType
import ru.otus.kotlin.walls.api.v1.models.IRequest
import ru.otus.kotlin.walls.api.v1.models.Status
import ru.otus.kotlin.walls.api.v1.models.Type

class RequestSerializationTest : FreeSpec({
    val request = AdCreateRequest(
        requestId = "123",
        debug = AdDebug(
            mode = AdRequestDebugMode.STUB,
            stub = AdRequestDebugStub.BAD_TITLE
        ),
        ad = AdCreateObject(
            title = "title",
            description = "description",
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
    val requestJson = """
        {
            "requestType": "create",
            "requestType": null,
            "requestId": "123",
            "debug": {
                "mode": "STUB",
                "stub": "BAD_TITLE"
            },
            "ad": {
                "active": null,
                "title": "title",
                "description": "description",
                "type": "APARTMENT",
                "status": "NEW",
                "area": 52.5,
                "price": 3520000,
                "buildingType": "BRICK",
                "hasLift": true,
                "roomsNumber": 3,
                "floor": 4
            }
        }
    """.trimIndent()

    "serialize" {
        val json = apiV1Mapper.writeValueAsString(request)

        json shouldEqualJson requestJson
    }

   "deserialize" {
        val json = apiV1Mapper.writeValueAsString(request)

        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as AdCreateRequest

       obj shouldBe request
    }

    "naked deserialize" {
        val obj = apiV1Mapper.readValue(requestJson, AdCreateRequest::class.java)

        obj shouldBe request
    }
})
