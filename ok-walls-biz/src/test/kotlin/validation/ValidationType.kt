package ru.otus.kotlin.walls.biz.validation

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.AdRealEstateType
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.stubs.AdStub

suspend fun validationTypeCorrect(command: AdCommand, processor: AdProcessor) {
    val type = AdRealEstateType.APARTMENT
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult { this.type = type },
    )

    processor.exec(ctx)

    ctx.errors.shouldBeEmpty()
    ctx.state shouldBe State.RUNNING
    ctx.adValidated.type shouldBe type
}

suspend fun validationTypeIncorrect(command: AdCommand, processor: AdProcessor) {
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult {
            type = AdRealEstateType.NONE
        },
    )

    processor.exec(ctx)

    ctx.errors.shouldContainExactly(
        AdError(
            code = "validation-type-no-content",
            field = "type",
            group = "validation",
            message = "Validation error for field type: field must contain value",
        )
    )
    ctx.state shouldBe State.FAILING
}
