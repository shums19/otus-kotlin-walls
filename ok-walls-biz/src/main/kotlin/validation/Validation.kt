package ru.otus.kotlin.walls.biz.validation

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.chain

fun ICorChainDsl<AdContext>.validation(block: ICorChainDsl<AdContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == State.RUNNING }
}
