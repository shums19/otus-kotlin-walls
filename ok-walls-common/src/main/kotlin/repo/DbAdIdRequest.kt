package ru.otus.kotlin.walls.common.repo

import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdId
import ru.otus.kotlin.walls.common.models.AdLock

data class DbAdIdRequest(
    val id: AdId,
    val lock: AdLock = AdLock.NONE,
) {
    constructor(ad: Ad): this(ad.id, ad.lock)
}
