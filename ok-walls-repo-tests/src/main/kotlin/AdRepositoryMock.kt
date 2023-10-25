package ru.otus.kotlin.walls.repo.tests

import ru.otus.kotlin.walls.common.repo.*

class AdRepositoryMock(
    private val invokeCreateAd: (DbAdRequest) -> DbAdResponse = { DbAdResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadAd: (DbAdIdRequest) -> DbAdResponse = { DbAdResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateAd: (DbAdRequest) -> DbAdResponse = { DbAdResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteAd: (DbAdIdRequest) -> DbAdResponse = { DbAdResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchAd: (DbAdSearchRequest) -> DbAdsResponse = { DbAdsResponse.MOCK_SUCCESS_EMPTY },
): IAdRepository {
    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        return invokeCreateAd(rq)
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        return invokeReadAd(rq)
    }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        return invokeUpdateAd(rq)
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        return invokeDeleteAd(rq)
    }

    override suspend fun searchAd(rq: DbAdSearchRequest): DbAdsResponse {
        return invokeSearchAd(rq)
    }
}
