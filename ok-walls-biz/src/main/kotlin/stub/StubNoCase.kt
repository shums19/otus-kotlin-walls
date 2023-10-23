package ru.otus.kotlin.walls.biz.stub

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.helpers.fail
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker

fun ICorChainDsl<AdContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == State.RUNNING }
    handle {
        fail(
            AdError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
