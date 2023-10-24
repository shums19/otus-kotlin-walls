package ru.otus.kotlin.walls.biz.validation

import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.CorSettings
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.AdFilter
import ru.otus.kotlin.walls.common.models.AdSearchString
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.repo.stubs.AdRepoStub

class BizValidationSearchTest : FreeSpec({
    val command = AdCommand.SEARCH

    val processor = AdProcessor(settings = CorSettings(repoTest = AdRepoStub()))

    "correct not empty" {
        val ctx = AdContext(
            command = command,
            state = State.NONE,
            workMode = WorkMode.TEST,
            adFilterRequest = AdFilter(searchString = AdSearchString("3-комнатная apartment, квартира_."))
        )

        processor.exec(ctx)

        ctx.errors.shouldBeEmpty()
        ctx.state shouldBe State.FINISHING
    }

    "correct empty" {
        val ctx = AdContext(
            command = command,
            state = State.NONE,
            workMode = WorkMode.TEST,
            adFilterRequest = AdFilter()
        )

        processor.exec(ctx)

        ctx.errors.shouldBeEmpty()
        ctx.state shouldBe State.FINISHING
    }

    "bad search string format" - {
        withData(
            "a".repeat(101),
            "123dff^&*",
        ) { searchString ->
            val ctx = AdContext(
                command = command,
                state = State.NONE,
                workMode = WorkMode.TEST,
                adFilterRequest = AdFilter(searchString = AdSearchString(searchString)),
            )

            processor.exec(ctx)

            ctx.errors.shouldContainExactly(
                AdError(
                    code = "validation-searchString-bad-content",
                    field = "searchString",
                    group = "validation",
                    message = "Validation error for field searchString: " +
                        "field must matches pattern=^[А-Яа-яёЁa-zA-Z0-9 _.,-]*\$ and length must not be more than 100",
                )
            )
            ctx.state shouldBe State.FAILING
        }
    }
})

