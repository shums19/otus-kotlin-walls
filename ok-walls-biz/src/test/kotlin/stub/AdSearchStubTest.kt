package ru.otus.kotlin.walls.biz.stub

import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdDescription
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.AdFilter
import ru.otus.kotlin.walls.common.models.AdId
import ru.otus.kotlin.walls.common.models.AdSearchString
import ru.otus.kotlin.walls.common.models.AdTitle
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.common.stubs.AdStubCase
import ru.otus.kotlin.walls.stubs.AdStub

class AdSearchStubTest : FreeSpec({
    val filter = "3-комнатная квартира"

    val processor = AdProcessor()

    "success" {
        val ctx = AdContext(
            command = AdCommand.SEARCH,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.SUCCESS,
            adFilterRequest = AdFilter(searchString = AdSearchString(filter)),
        )

        processor.exec(ctx)

        ctx.adsResponse.shouldContainExactly(
            AdStub.prepareResult {
                this.id = AdId("5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a")
                this.title = AdTitle("$filter 5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a")
                this.description = AdDescription("desc $filter 5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a")
            },
            AdStub.prepareResult {
                this.id = AdId("51f51c82-049d-45b7-9e41-68fc0121c90f")
                this.title = AdTitle("$filter 51f51c82-049d-45b7-9e41-68fc0121c90f")
                this.description = AdDescription("desc $filter 51f51c82-049d-45b7-9e41-68fc0121c90f")
            },
            AdStub.prepareResult {
                this.id = AdId("8b32d0fe-6527-11ee-8c99-0242ac120002")
                this.title = AdTitle("$filter 8b32d0fe-6527-11ee-8c99-0242ac120002")
                this.description = AdDescription("desc $filter 8b32d0fe-6527-11ee-8c99-0242ac120002")
            },
            AdStub.prepareResult {
                this.id = AdId("96cd781a-6527-11ee-8c99-0242ac120002")
                this.title = AdTitle("$filter 96cd781a-6527-11ee-8c99-0242ac120002")
                this.description = AdDescription("desc $filter 96cd781a-6527-11ee-8c99-0242ac120002")
            },
            AdStub.prepareResult {
                this.id = AdId("276004ea-b726-4a9b-bec7-37b8bb0852e6")
                this.title = AdTitle("$filter 276004ea-b726-4a9b-bec7-37b8bb0852e6")
                this.description = AdDescription("desc $filter 276004ea-b726-4a9b-bec7-37b8bb0852e6")
            },
        )
    }

    "bad search string" {
        val ctx = AdContext(
            command = AdCommand.SEARCH,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.BAD_SEARCH_STRING,
            adFilterRequest = AdFilter(searchString = AdSearchString(filter)),
        )
        processor.exec(ctx)

        ctx.adResponse shouldBe Ad()
        ctx.errors.shouldContainExactly(
            AdError(
                group = "validation",
                code = "validation-search-string",
                field = "searchString",
                message = "Wrong searchString field"
            )
        )
    }

    "not found" {
        val ctx = AdContext(
            command = AdCommand.SEARCH,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.NOT_FOUND,
            adFilterRequest = AdFilter(searchString = AdSearchString(filter)),
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
            command = AdCommand.SEARCH,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = AdStubCase.DB_ERROR,
            adFilterRequest = AdFilter(searchString = AdSearchString(filter)),
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
            AdStubCase.BAD_ID,
            AdStubCase.BAD_BUILDING_TYPE,
            AdStubCase.BAD_STATUS,
            AdStubCase.BAD_FLOOR,
            AdStubCase.BAD_ROOMS_NUMBER,
            AdStubCase.BAD_DESCRIPTION,
            AdStubCase.BAD_AREA,
            AdStubCase.BAD_PRICE,
            AdStubCase.BAD_TITLE,
            AdStubCase.BAD_TYPE,
        ) { stubCase ->
            val ctx = AdContext(
                command = AdCommand.SEARCH,
                state = State.NONE,
                workMode = WorkMode.STUB,
                stubCase = stubCase,
                adFilterRequest = AdFilter(searchString = AdSearchString(filter)),
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
