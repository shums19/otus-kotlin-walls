package ru.otus.kotlin.walls.biz.validation

import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker
import ru.otus.kotlin.walls.common.helpers.errorValidation
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.helpers.fail

fun ICorChainDsl<AdContext>.validateTitleHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { adValidating.title.value.isNotEmpty() && !adValidating.title.value.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "title",
                violationCode = "no-content",
                description = "field must contain letters"
            )
        )
    }
}
