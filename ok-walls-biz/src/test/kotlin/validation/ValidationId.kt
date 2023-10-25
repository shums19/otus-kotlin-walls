package ru.otus.kotlin.walls.biz.validation

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.biz.addTestPrincipal
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.AdId
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.stubs.AdStub

suspend fun validationIdCorrect(command: AdCommand, processor: AdProcessor) {
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.get(),
    )
    ctx.addTestPrincipal(ctx.adRequest.ownerId)

    processor.exec(ctx)

    ctx.errors.shouldBeEmpty()
    ctx.state shouldBe State.FINISHING
}

suspend fun validationIdTrim(command: AdCommand, processor: AdProcessor) {
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult {
            id = AdId(" \n\t 5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a \n\t ")
        },
    )
    ctx.addTestPrincipal(ctx.adRequest.ownerId)

    processor.exec(ctx)

    ctx.errors.shouldBeEmpty()
    ctx.state shouldBe State.FINISHING
    ctx.adValidated.id shouldBe AdId("5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a")
}

suspend fun validationIdEmpty(command: AdCommand, processor: AdProcessor) {
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult {
            id = AdId("")
        },
    )
    ctx.addTestPrincipal(ctx.adRequest.ownerId)

    processor.exec(ctx)

    ctx.errors.shouldContainExactly(
        AdError(
            code = "validation-id-empty",
            field = "id",
            group = "validation",
            message = "Validation error for field id: field must not be empty",
        )
    )
    ctx.state shouldBe State.FAILING
}

suspend fun validationIdFormat(command: AdCommand, processor: AdProcessor) {
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult {
            id = AdId("!@#\$%^&*(),.{}")
        },
    )
    ctx.addTestPrincipal(ctx.adRequest.ownerId)

    processor.exec(ctx)

    ctx.errors.shouldContainExactly(
        AdError(
            code = "validation-id-bad-format",
            field = "id",
            group = "validation",
            message = "Validation error for field id: value !@#\$%^&*(),.{} must be uuid",
        )
    )
    ctx.state shouldBe State.FAILING
}
