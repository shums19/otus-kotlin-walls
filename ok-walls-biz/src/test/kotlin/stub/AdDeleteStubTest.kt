package ru.otus.kotlin.walls.biz.stub

import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.AdId
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.common.stubs.AdStubCase
import ru.otus.kotlin.walls.stubs.AdStub
import java.util.UUID

class AdDeleteStubTest : FreeSpec({
    val id = AdId(UUID.randomUUID().toString())

    val processor = AdProcessor()

    "success" {
        val ctx = AdContext(
            command = AdCommand.DELETE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.SUCCESS,
            adRequest = Ad(id = id),
        )

        processor.exec(ctx)

        ctx.adResponse shouldBe AdStub.prepareResult { this.id = id }
        ctx.errors.shouldBeEmpty()
    }

    "bad id" {
        val ctx = AdContext(
            command = AdCommand.DELETE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.BAD_ID,
            adRequest = Ad(id = id),
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

    "not found error" {
        val ctx = AdContext(
            command = AdCommand.DELETE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.NOT_FOUND,
            adRequest = Ad(id = id),
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
            command = AdCommand.DELETE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.DB_ERROR,
            adRequest = Ad(id = id),
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
            AdStubCase.BAD_BUILDING_TYPE,
            AdStubCase.BAD_STATUS,
            AdStubCase.BAD_FLOOR,
            AdStubCase.BAD_SEARCH_STRING,
            AdStubCase.BAD_ROOMS_NUMBER,
            AdStubCase.BAD_DESCRIPTION,
            AdStubCase.BAD_AREA,
            AdStubCase.BAD_PRICE,
            AdStubCase.BAD_TITLE,
            AdStubCase.BAD_TYPE,
        ) { stubCase ->
            val ctx = AdContext(
                command = AdCommand.DELETE,
                state = State.NONE,
                workMode = WorkMode.STUB,
                stubCase = stubCase,
                adRequest = Ad(id = id),
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
