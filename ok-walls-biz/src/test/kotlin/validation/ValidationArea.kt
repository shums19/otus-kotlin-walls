package ru.otus.kotlin.walls.biz.validation

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.biz.addTestPrincipal
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdArea
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.stubs.AdStub

suspend fun validationAreaCorrect(command: AdCommand, processor: AdProcessor) {
    val area = AdArea(60.5.toBigDecimal())
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult { this.area = area },
    )
    ctx.addTestPrincipal(ctx.adRequest.ownerId)

    processor.exec(ctx)

    ctx.errors.shouldBeEmpty()
    ctx.state shouldBe State.FINISHING
    ctx.adValidated.area shouldBe area
}

suspend fun validationAreaIncorrect(command: AdCommand, processor: AdProcessor) {
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult {
            area = AdArea((-10000).toBigDecimal())
        },
    )
    ctx.addTestPrincipal(ctx.adRequest.ownerId)

    processor.exec(ctx)

    ctx.errors.shouldContainExactly(
        AdError(
            code = "validation-area-non-positive-number",
            field = "area",
            group = "validation",
            message = "Validation error for field area: field must be positive number",
        )
    )
    ctx.state shouldBe State.FAILING
}
