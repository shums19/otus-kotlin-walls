package ru.otus.kotlin.walls.biz.validation

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.AdRoomsNumber
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.stubs.AdStub

suspend fun validationRoomsNumberCorrect(command: AdCommand, processor: AdProcessor) {
    val roomsNumber = AdRoomsNumber(3)
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult { this.roomsNumber = roomsNumber },
    )

    processor.exec(ctx)

    ctx.errors.shouldBeEmpty()
    ctx.state shouldBe State.RUNNING
    ctx.adValidated.roomsNumber shouldBe roomsNumber
}

suspend fun validationRoomsNumberIncorrect(command: AdCommand, processor: AdProcessor) {
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult {
            roomsNumber = AdRoomsNumber(-1)
        },
    )

    processor.exec(ctx)

    ctx.errors.shouldContainExactly(
        AdError(
            code = "validation-roomsNumber-non-positive-number",
            field = "roomsNumber",
            group = "validation",
            message = "Validation error for field roomsNumber: field must be positive number",
        )
    )
    ctx.state shouldBe State.FAILING
}
