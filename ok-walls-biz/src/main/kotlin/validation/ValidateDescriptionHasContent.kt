package ru.otus.kotlin.walls.biz.validation

import ru.otus.kotlin.walls.common.helpers.errorValidation
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.helpers.fail
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker

fun ICorChainDsl<AdContext>.validateDescriptionHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { adValidating.description.value.isNotEmpty() && !adValidating.description.value.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "description",
                violationCode = "no-content",
                description = "field must contain letters"
            )
        )
    }
}
