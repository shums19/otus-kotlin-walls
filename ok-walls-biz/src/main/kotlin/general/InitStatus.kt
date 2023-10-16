package ru.otus.kotlin.walls.biz.general

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker

fun ICorChainDsl<AdContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == State.NONE }
    handle { state = State.RUNNING }
}
