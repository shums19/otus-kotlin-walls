package ru.otus.kotlin.walls.mappers.v1

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import ru.otus.kotlin.walls.api.v1.models.AdCreateObject
import ru.otus.kotlin.walls.api.v1.models.AdCreateRequest
import ru.otus.kotlin.walls.api.v1.models.AdCreateResponse
import ru.otus.kotlin.walls.api.v1.models.AdDebug
import ru.otus.kotlin.walls.api.v1.models.AdRequestDebugMode
import ru.otus.kotlin.walls.api.v1.models.AdRequestDebugStub
import ru.otus.kotlin.walls.api.v1.models.BuildingType
import ru.otus.kotlin.walls.api.v1.models.ResponseResult
import ru.otus.kotlin.walls.api.v1.models.Status
import ru.otus.kotlin.walls.api.v1.models.Type
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdArea
import ru.otus.kotlin.walls.common.models.AdBuildingType
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdDescription
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.AdFloor
import ru.otus.kotlin.walls.common.models.AdId
import ru.otus.kotlin.walls.common.models.AdPrice
import ru.otus.kotlin.walls.common.models.AdRealEstateStatus
import ru.otus.kotlin.walls.common.models.AdRealEstateType
import ru.otus.kotlin.walls.common.models.AdRoomsNumber
import ru.otus.kotlin.walls.common.models.AdTitle
import ru.otus.kotlin.walls.common.models.RequestId
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.common.stubs.AdStubCase

class MapperTest : FreeSpec({
    "fromTransport" {
        val req = AdCreateRequest(
            requestId = "1234",
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStub.SUCCESS,
            ),
            ad = AdCreateObject(
                title = "title",
                description = "desc",
                active = true,
                hasLift = true,
                area = 52.toBigDecimal(),
                price = 3250000.toBigDecimal(),
                roomsNumber = 3,
                floor = 4,
                type = Type.APARTMENT,
                status = Status.NEW,
                buildingType = BuildingType.BRICK,
            ),
        )

        val context = AdContext()
        context.fromTransport(req)

        context.apply {
            this.requestId shouldBe RequestId("1234")
            this.command shouldBe AdCommand.CREATE
            this.stubCase shouldBe AdStubCase.SUCCESS
            this.workMode shouldBe WorkMode.STUB
            this.adRequest.id shouldBe AdId.NONE
            this.adRequest.title shouldBe AdTitle("title")
            this.adRequest.description shouldBe AdDescription("desc")
            this.adRequest.isActive shouldBe true
            this.adRequest.hasLift shouldBe true
            this.adRequest.area shouldBe AdArea(52.toBigDecimal())
            this.adRequest.price shouldBe AdPrice(3250000.toBigDecimal())
            this.adRequest.roomsNumber shouldBe AdRoomsNumber(3)
            this.adRequest.floor shouldBe AdFloor(4)
            this.adRequest.type shouldBe AdRealEstateType.APARTMENT
            this.adRequest.status shouldBe AdRealEstateStatus.NEW
            this.adRequest.buildingType shouldBe AdBuildingType.BRICK
        }
    }

    "toTransport" {
        val context = AdContext(
            requestId = RequestId("1234"),
            command = AdCommand.CREATE,
            adResponse = Ad(
                title = AdTitle("title"),
                description = AdDescription("desc"),
                isActive = true,
                hasLift = true,
                area = AdArea(52.toBigDecimal()),
                price = AdPrice(3250000.toBigDecimal()),
                roomsNumber = AdRoomsNumber(3),
                floor = AdFloor(4),
                type = AdRealEstateType.APARTMENT,
                status = AdRealEstateStatus.NEW,
                buildingType = AdBuildingType.BRICK,
            ),
            errors = mutableListOf(
                AdError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = State.RUNNING,
        )

        val req = context.toTransport() as AdCreateResponse

        req.apply {
            this.requestId shouldBe "1234"
            this.ad?.id shouldBe null
            this.ad?.title shouldBe "title"
            this.ad?.description shouldBe "desc"
            this.ad?.active shouldBe true
            this.ad?.hasLift shouldBe true
            this.ad?.area shouldBe 52.toBigDecimal()
            this.ad?.price shouldBe 3250000.toBigDecimal()
            this.ad?.roomsNumber shouldBe 3
            this.ad?.floor shouldBe 4
            this.ad?.type shouldBe Type.APARTMENT
            this.ad?.status shouldBe Status.NEW
            this.ad?.buildingType shouldBe BuildingType.BRICK
            this.errors?.size shouldBe 1
            this.errors?.firstOrNull()?.code shouldBe "err"
            this.errors?.firstOrNull()?.group shouldBe "request"
            this.errors?.firstOrNull()?.field shouldBe "title"
            this.errors?.firstOrNull()?.message shouldBe "wrong title"
            this.result shouldBe ResponseResult.ERROR
        }
    }
})
