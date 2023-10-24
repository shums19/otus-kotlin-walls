package ru.otus.kotlin.walls.common.repo

import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdError

data class DbAdsResponse(
    override val data: List<Ad>?,
    override val isSuccess: Boolean,
    override val errors: List<AdError> = emptyList(),
): IDbResponse<List<Ad>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbAdsResponse(emptyList(), true)
        fun success(result: List<Ad>) = DbAdsResponse(result, true)
        fun error(errors: List<AdError>) = DbAdsResponse(null, false, errors)
        fun error(error: AdError) = DbAdsResponse(null, false, listOf(error))
    }
}
