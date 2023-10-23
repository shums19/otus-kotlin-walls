package ru.otus.kotlin.walls.biz.stub

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.stubs.AdStubCase
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker

fun ICorChainDsl<AdContext>.stubValidationBadDescription(title: String) = worker {
    this.title = title
    on { stubCase == AdStubCase.BAD_DESCRIPTION && state == State.RUNNING }
    handle {
        state = State.FAILING
        this.errors.add(
            AdError(
                group = "validation",
                code = "validation-description",
                field = "description",
                message = "Wrong description field"
            )
        )
    }
}
