package ru.otus.kotlin.walls.biz.validation

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker

fun ICorChainDsl<AdContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == State.RUNNING }
    handle {
        adValidated = adValidating
    }
}

fun ICorChainDsl<AdContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == State.RUNNING }
    handle {
        adFilterValidated = adFilterValidating
    }
}
