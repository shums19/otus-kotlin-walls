package ru.otus.kotlin.walls.common.helpers

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.exceptions.RepoConcurrencyException
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.AdLock
import ru.otus.kotlin.walls.common.models.State

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

fun AdContext.fail(error: AdError) {
    addError(error)
    state = State.FAILING
}

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, bad-symbols, too-long, etc
     */
    violationCode: String,
    description: String,
    level: AdError.Level = AdError.Level.ERROR,
) = AdError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    exception: Exception? = null,
    level: AdError.Level = AdError.Level.ERROR,
) = AdError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
    exception = exception,
)

fun errorRepoConcurrency(
    expectedLock: AdLock,
    actualLock: AdLock?,
    exception: Exception? = null,
) = AdError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)
