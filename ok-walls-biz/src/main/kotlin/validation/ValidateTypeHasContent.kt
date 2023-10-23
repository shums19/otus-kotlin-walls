package ru.otus.kotlin.walls.biz.validation

import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker
import ru.otus.kotlin.walls.common.helpers.errorValidation
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.helpers.fail
import ru.otus.kotlin.walls.common.models.AdRealEstateType

fun ICorChainDsl<AdContext>.validateTypeHasContent(title: String) = worker {
    this.title = title

    on { adValidating.type == AdRealEstateType.NONE }
    handle {
        fail(
            errorValidation(
                field = "type",
                violationCode = "no-content",
                description = "field must contain value"
            )
        )
    }
}
