package ru.otus.kotlin.walls.biz.general

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.chain

fun ICorChainDsl<AdContext>.operation(
    title: String,
    command: AdCommand,
    block: ICorChainDsl<AdContext>.() -> Unit,
) = chain {
    block()
    this.title = title
    on { this.command == command && state == State.RUNNING }
}
