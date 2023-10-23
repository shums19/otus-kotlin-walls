package ru.otus.kotlin.walls.biz.validation

import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker
import ru.otus.kotlin.walls.common.helpers.errorValidation
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.helpers.fail

fun ICorChainDsl<AdContext>.validateFloorPositive(title: String) = worker {
    this.title = title
    on { adValidating.floor.value <= 0 }
    handle {
        fail(
            errorValidation(
                field = "floor",
                violationCode = "non-positive-number",
                description = "field must be positive number"
            )
        )
    }
}
