package ru.otus.kotlin.walls.biz.stub

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.stubs.AdStubCase
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker

fun ICorChainDsl<AdContext>.stubValidationBadStatus(title: String) = worker {
    this.title = title
    on { stubCase == AdStubCase.BAD_STATUS && state == State.RUNNING }
    handle {
        state = State.FAILING
        this.errors.add(
            AdError(
                group = "validation",
                code = "validation-status",
                field = "status",
                message = "Wrong status field"
            )
        )
    }
}
