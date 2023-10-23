package ru.otus.kotlin.walls.biz.stub

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.stubs.AdStubCase
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker
import ru.otus.kotlin.walls.stubs.AdStub

fun ICorChainDsl<AdContext>.stubDeleteSuccess(title: String) = worker {
    this.title = title
    on { stubCase == AdStubCase.SUCCESS && state == State.RUNNING }
    handle {
        state = State.FINISHING
        val stub = AdStub.prepareResult {
            adRequest.id.let { id ->
                id.value.takeIf { it.isNotBlank() }?.also { this.id = id }
            }
        }
        adResponse = stub
    }
}
