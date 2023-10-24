package ru.otus.kotlin.walls.biz.validation

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.helpers.errorValidation
import ru.otus.kotlin.walls.common.helpers.fail
import ru.otus.kotlin.walls.common.models.AdLock
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker

fun ICorChainDsl<AdContext>.validateLockProperFormat(title: String) = worker {
    this.title = title
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { adValidating.lock != AdLock.NONE && !adValidating.lock.value.matches(regExp) }
    handle {
        val encodedId = adValidating.lock.value
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}
