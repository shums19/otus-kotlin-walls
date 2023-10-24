package ru.otus.kotlin.walls.repo.stubs

import ru.otus.kotlin.walls.common.repo.DbAdIdRequest
import ru.otus.kotlin.walls.common.repo.DbAdRequest
import ru.otus.kotlin.walls.common.repo.DbAdResponse
import ru.otus.kotlin.walls.common.repo.DbAdSearchRequest
import ru.otus.kotlin.walls.common.repo.DbAdsResponse
import ru.otus.kotlin.walls.common.repo.IAdRepository
import ru.otus.kotlin.walls.stubs.AdStub


class AdRepoStub() : IAdRepository {
    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        return DbAdResponse(
            data = AdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        return DbAdResponse(
            data = AdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        return DbAdResponse(
            data = AdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        return DbAdResponse(
            data = AdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchAd(rq: DbAdSearchRequest): DbAdsResponse {
        return DbAdsResponse(
            data = AdStub.prepareSearchList(filter = ""),
            isSuccess = true,
        )
    }
}
