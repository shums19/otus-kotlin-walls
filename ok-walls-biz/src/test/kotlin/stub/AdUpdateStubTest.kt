package ru.otus.kotlin.walls.biz.stub

import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdArea
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdDescription
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.AdId
import ru.otus.kotlin.walls.common.models.AdPrice
import ru.otus.kotlin.walls.common.models.AdRealEstateType
import ru.otus.kotlin.walls.common.models.AdTitle
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.common.stubs.AdStubCase
import ru.otus.kotlin.walls.stubs.AdStub
import java.util.UUID

class AdUpdateStubTest : FreeSpec({
    val id = AdId(UUID.randomUUID().toString())

    val processor = AdProcessor()

    "success" {
        val title = AdTitle("title $id")
        val description = AdDescription("desc $id")
        val type = AdRealEstateType.DACHA
        val price = AdPrice((-50).toBigDecimal())
        val area = AdArea(10.toBigDecimal())

        val ctx = AdContext(
            command = AdCommand.UPDATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.SUCCESS,
            adRequest = Ad(
                id = id,
                title = title,
                description = description,
                type = type,
                price = price,
                area = area,
            ),
        )

        processor.exec(ctx)

        ctx.adResponse shouldBe AdStub.prepareResult {
            this.id = id
            this.title = title
            this.description = description
            this.type = type
            this.area = area
        }
    }

    "bad id" {
        val ctx = AdContext(
            command = AdCommand.UPDATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.BAD_ID,
            adRequest = AdStub.get(),
        )
        processor.exec(ctx)

        ctx.adResponse shouldBe Ad()
        ctx.errors.shouldContainExactly(
            AdError(
                group = "validation",
                code = "validation-id",
                field = "id",
                message = "Wrong id field"
            )
        )
    }

    "bad title" {
        val ctx = AdContext(
            command = AdCommand.UPDATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.BAD_TITLE,
            adRequest = AdStub.get(),
        )
        processor.exec(ctx)

        ctx.adResponse shouldBe Ad()
        ctx.errors.shouldContainExactly(
            AdError(
                group = "validation",
                code = "validation-title",
                field = "title",
                message = "Wrong title field"
            )
        )
    }

    "bad description" {
        val ctx = AdContext(
            command = AdCommand.UPDATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.BAD_DESCRIPTION,
            adRequest = AdStub.get(),
        )
        processor.exec(ctx)

        ctx.adResponse shouldBe Ad()
        ctx.errors.shouldContainExactly(
            AdError(
                group = "validation",
                code = "validation-description",
                field = "description",
                message = "Wrong description field"
            )
        )
    }

    "bad building type" {
        val ctx = AdContext(
            command = AdCommand.UPDATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.BAD_BUILDING_TYPE,
            adRequest = AdStub.get(),
        )
        processor.exec(ctx)

        ctx.adResponse shouldBe Ad()
        ctx.errors.shouldContainExactly(
            AdError(
                group = "validation",
                code = "validation-building-type",
                field = "buildingType",
                message = "Wrong buildingType field"
            )
        )
    }

    "bad price" {
        val ctx = AdContext(
            command = AdCommand.UPDATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.BAD_PRICE,
            adRequest = AdStub.get(),
        )
        processor.exec(ctx)

        ctx.adResponse shouldBe Ad()
        ctx.errors.shouldContainExactly(
            AdError(
                group = "validation",
                code = "validation-price",
                field = "price",
                message = "Wrong price field"
            )
        )
    }

    "bad area" {
        val ctx = AdContext(
            command = AdCommand.UPDATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.BAD_AREA,
            adRequest = AdStub.get(),
        )
        processor.exec(ctx)

        ctx.adResponse shouldBe Ad()
        ctx.errors.shouldContainExactly(
            AdError(
                group = "validation",
                code = "validation-area",
                field = "area",
                message = "Wrong area field"
            )
        )
    }

    "bad floor" {
        val ctx = AdContext(
            command = AdCommand.UPDATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.BAD_FLOOR,
            adRequest = AdStub.get(),
        )
        processor.exec(ctx)

        ctx.adResponse shouldBe Ad()
        ctx.errors.shouldContainExactly(
            AdError(
                group = "validation",
                code = "validation-floor",
                field = "floor",
                message = "Wrong floor field"
            )
        )
    }

    "bad rooms number" {
        val ctx = AdContext(
            command = AdCommand.UPDATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.BAD_ROOMS_NUMBER,
            adRequest = AdStub.get(),
        )
        processor.exec(ctx)

        ctx.adResponse shouldBe Ad()
        ctx.errors.shouldContainExactly(
            AdError(
                group = "validation",
                code = "validation-rooms-number",
                field = "roomsNumber",
                message = "Wrong roomsNumber field"
            )
        )
    }

    "bad status" {
        val ctx = AdContext(
            command = AdCommand.UPDATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.BAD_STATUS,
            adRequest = AdStub.get(),
        )
        processor.exec(ctx)

        ctx.adResponse shouldBe Ad()
        ctx.errors.shouldContainExactly(
            AdError(
                group = "validation",
                code = "validation-status",
                field = "status",
                message = "Wrong status field"
            )
        )
    }

    "bad type" {
        val ctx = AdContext(
            command = AdCommand.UPDATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.BAD_TYPE,
            adRequest = AdStub.get(),
        )
        processor.exec(ctx)

        ctx.adResponse shouldBe Ad()
        ctx.errors.shouldContainExactly(
            AdError(
                group = "validation",
                code = "validation-type",
                field = "type",
                message = "Wrong type field"
            )
        )
    }

    "not found error" {
        val ctx = AdContext(
            command = AdCommand.UPDATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.NOT_FOUND,
            adRequest = AdStub.get(),
        )

        processor.exec(ctx)

        ctx.adResponse shouldBe Ad()
        ctx.errors.shouldContainExactly(
            AdError(
                group = "not-found",
                code = "not-found",
                message = "Not found error"
            )
        )
    }

    "database error" {
        val ctx = AdContext(
            command = AdCommand.UPDATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.DB_ERROR,
            adRequest = AdStub.get(),
        )

        processor.exec(ctx)

        ctx.adResponse shouldBe Ad()
        ctx.errors.shouldContainExactly(
            AdError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }

    "no case" - {
        withData(
            AdStubCase.NONE,
            AdStubCase.BAD_SEARCH_STRING,
        ) { stubCase ->
            val ctx = AdContext(
                command = AdCommand.UPDATE,
                state = State.NONE,
                workMode = WorkMode.STUB,
                stubCase = stubCase,
                adRequest = AdStub.get(),
            )

            processor.exec(ctx)

            ctx.adResponse shouldBe Ad()
            ctx.errors.shouldContainExactly(
                AdError(
                    code = "validation",
                    field = "stub",
                    group = "validation",
                    message = "Wrong stub case is requested: ${stubCase.name}"
                )
            )
        }
    }
})
