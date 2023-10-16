package ru.otus.kotlin.walls.biz.validation

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.AdPrice
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.stubs.AdStub

suspend fun validationPriceCorrect(command: AdCommand, processor: AdProcessor) {
    val price = AdPrice(3000000.toBigDecimal())
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult { this.price = price },
    )

    processor.exec(ctx)

    ctx.errors.shouldBeEmpty()
    ctx.state shouldBe State.RUNNING
    ctx.adValidated.price shouldBe price
}

suspend fun validationPriceIncorrect(command: AdCommand, processor: AdProcessor) {
    val ctx = AdContext(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        adRequest = AdStub.prepareResult {
            price = AdPrice((-10000).toBigDecimal())
        },
    )

    processor.exec(ctx)

    ctx.errors.shouldContainExactly(
        AdError(
            code = "validation-price-non-positive-number",
            field = "price",
            group = "validation",
            message = "Validation error for field price: field must be positive number",
        )
    )
    ctx.state shouldBe State.FAILING
}
