package ru.otus.kotlin.walls.biz.repo

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.repo.DbAdIdRequest
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker

fun ICorChainDsl<AdContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == State.RUNNING }
    handle {
        val request = DbAdIdRequest(adValidated)
        val result = adRepo.readAd(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            adRepoRead = resultAd
        } else {
            state = State.FAILING
            errors.addAll(result.errors)
        }
    }
}
