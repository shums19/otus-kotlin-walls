package ru.otus.kotlin.walls.common.repo

import ru.otus.kotlin.walls.common.helpers.errorRepoConcurrency
import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdLock
import ru.otus.kotlin.walls.common.models.AdError

data class DbAdResponse(
    override val data: Ad?,
    override val isSuccess: Boolean,
    override val errors: List<AdError> = emptyList()
): IDbResponse<Ad> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbAdResponse(null, true)
        fun success(result: Ad) = DbAdResponse(result, true)
        fun error(errors: List<AdError>, data: Ad? = null) = DbAdResponse(data, false, errors)
        fun error(error: AdError, data: Ad? = null) = DbAdResponse(data, false, listOf(error))

        val errorEmptyId = error(
            AdError(
                field = "id",
                message = "Not Found",
                code = "not-found"
            )
        )

        fun errorConcurrent(lock: AdLock, ad: Ad?) = error(
            errorRepoConcurrency(lock, ad?.lock?.let { AdLock(it.value) }),
            ad
        )

        val errorNotFound = error(
            AdError(
                field = "id",
                message = "Id must not be null or blank"
            )
        )
    }
}
