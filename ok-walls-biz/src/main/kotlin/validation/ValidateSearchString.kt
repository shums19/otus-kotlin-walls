package ru.otus.kotlin.walls.biz.validation

import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker
import ru.otus.kotlin.walls.common.helpers.errorValidation
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.helpers.fail

fun ICorChainDsl<AdContext>.validateSearchString(title: String) = worker {
    this.title = title
    val maxLength = 100
    val regExp = "^[А-Яа-яёЁa-zA-Z0-9 _.,-]*$".toRegex()
    on {
        adFilterValidating.searchString.value.length > maxLength
            || !regExp.matches(adFilterValidating.searchString.value)
    }
    handle {
        fail(
            errorValidation(
                field = "searchString",
                violationCode = "bad-content",
                description = "field must matches pattern=$regExp and length must not be more than $maxLength"
            )
        )
    }
}
