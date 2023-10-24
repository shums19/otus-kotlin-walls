package ru.otus.kotlin.walls.repo.tests

import ru.otus.kotlin.walls.common.models.*
import ru.otus.kotlin.walls.stubs.AdStub

abstract class BaseInitAds(val op: String): IInitObjects<Ad> {

    open val lockOld: AdLock = AdLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: AdLock = AdLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        ownerId: UserId = UserId("owner-123"),
        lock: AdLock = lockOld,
    ) = AdStub.prepareResult {
        this.id = AdId("ad-repo-$op-$suf")
        this.title = AdTitle("$suf stub")
        this.description = AdDescription("$suf stub description")
        this.ownerId = ownerId
        this.lock = lock
        this.permissionsClient.clear()
    }
}
