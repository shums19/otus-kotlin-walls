package ru.otus.kotlin.walls.common.helpers

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdError

fun Throwable.asAdError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = AdError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun AdContext.addError(vararg error: AdError) = errors.addAll(error)
