package ru.otus.kotlin.walls.biz.validation

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdTitle
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker

fun ICorChainDsl<AdContext>.cleanTitle(title: String) = worker {
    this.title = title

    handle {
        this.adValidating.title = AdTitle(adValidating.title.value.trim())
    }
}
