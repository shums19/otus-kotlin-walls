package ru.otus.kotlin.walls.stubs

import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdDescription
import ru.otus.kotlin.walls.common.models.AdId
import ru.otus.kotlin.walls.common.models.AdTitle
import ru.otus.kotlin.walls.stubs.AdStubApatment.AD_APARTMENT

object AdStub {
    fun get(): Ad = AD_APARTMENT.copy()

    fun prepareSearchList(filter: String) = listOf(
        ad(id = "5ab73e69-c92f-4bc9-8f7a-4b483ae87b0a", filter = filter),
        ad(id = "51f51c82-049d-45b7-9e41-68fc0121c90f", filter = filter),
        ad(id = "8b32d0fe-6527-11ee-8c99-0242ac120002", filter = filter),
        ad(id = "96cd781a-6527-11ee-8c99-0242ac120002", filter = filter),
        ad(id = "276004ea-b726-4a9b-bec7-37b8bb0852e6", filter = filter),
    )

    private fun ad(id: String, filter: String) = AD_APARTMENT.copy(
        id = AdId(id),
        title = AdTitle("$filter $id"),
        description = AdDescription("desc $filter $id"),
    )
}
