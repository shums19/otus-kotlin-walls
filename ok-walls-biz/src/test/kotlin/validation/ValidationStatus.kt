package ru.otus.kotlin.walls.biz.validation

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.AdRealEstateStatus
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.stubs.AdStub

suspend fun validationStatusCorrect(command: AdCommand, processor: AdProcessor) {
    val status = AdRealEstateStatus.NEW
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult { this.status = status },
    )

    processor.exec(ctx)

    ctx.errors.shouldBeEmpty()
    ctx.state shouldBe State.RUNNING
    ctx.adValidated.status shouldBe status
}

suspend fun validationStatusIncorrect(command: AdCommand, processor: AdProcessor) {
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult {
            status = AdRealEstateStatus.NONE
        },
    )

    processor.exec(ctx)

    ctx.errors.shouldContainExactly(
        AdError(
            code = "validation-status-no-content",
            field = "status",
            group = "validation",
            message = "Validation error for field status: field must contain value",
        )
    )
    ctx.state shouldBe State.FAILING
}
