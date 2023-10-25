package ru.otus.kotlin.walls.biz.validation

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.biz.addTestPrincipal
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdDescription
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.AdTitle
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.stubs.AdStub

suspend fun validationDescriptionCorrect(command: AdCommand, processor: AdProcessor) {
    val description = AdDescription("Квартира в центре")
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult { this.description = description },
    )
    ctx.addTestPrincipal(ctx.adRequest.ownerId)

    processor.exec(ctx)

    ctx.errors.shouldBeEmpty()
    ctx.state shouldBe State.FINISHING
    ctx.adValidated.description shouldBe description
}

suspend fun validationDescriptionTrim(command: AdCommand, processor: AdProcessor) {
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult {
            description = AdDescription("    Квартира в центре     ")
        },
    )
    ctx.addTestPrincipal(ctx.adRequest.ownerId)

    processor.exec(ctx)

    ctx.errors.shouldBeEmpty()
    ctx.state shouldBe State.FINISHING
    ctx.adValidated.description shouldBe AdDescription("Квартира в центре")
}

suspend fun validationDescriptionEmpty(command: AdCommand, processor: AdProcessor) {
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult {
            description = AdDescription("")
        },
    )

    processor.exec(ctx)

    ctx.errors.shouldContainExactly(
        AdError(
            code = "validation-description-empty",
            field = "description",
            group = "validation",
            message = "Validation error for field description: field must not be empty",
        )
    )
    ctx.state shouldBe State.FAILING
}

suspend fun validationDescriptionSymbols(command: AdCommand, processor: AdProcessor) {
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult {
            description = AdDescription("!@#$%^&*(),.{}")
        },
    )

    processor.exec(ctx)

    ctx.errors.shouldContainExactly(
        AdError(
            code = "validation-description-no-content",
            field = "description",
            group = "validation",
            message = "Validation error for field description: field must contain letters",
        )
    )
    ctx.state shouldBe State.FAILING
}
