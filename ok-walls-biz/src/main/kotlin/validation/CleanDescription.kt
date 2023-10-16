package ru.otus.kotlin.walls.biz.validation

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdDescription
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker

fun ICorChainDsl<AdContext>.cleanDescription(title: String) = worker {
    this.title = title

    handle {
        this.adValidating.description = AdDescription(adValidating.description.value.trim())
    }
}
