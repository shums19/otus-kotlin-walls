package ru.otus.kotlin.walls.repo.cassandra.model

import ru.otus.kotlin.walls.common.models.AdRealEstateType

enum class RepoAdRealEstateType {
    APARTMENT,
    HOUSE,
    TOWNHOUSE,
    DACHA,
    OTHER,
}

fun RepoAdRealEstateType?.fromTransport(): AdRealEstateType = when(this) {
    null -> AdRealEstateType.NONE
    RepoAdRealEstateType.APARTMENT -> AdRealEstateType.APARTMENT
    RepoAdRealEstateType.HOUSE -> AdRealEstateType.HOUSE
    RepoAdRealEstateType.TOWNHOUSE -> AdRealEstateType.TOWNHOUSE
    RepoAdRealEstateType.DACHA -> AdRealEstateType.DACHA
    RepoAdRealEstateType.OTHER -> AdRealEstateType.OTHER
}

fun AdRealEstateType.toTransport(): RepoAdRealEstateType? = when(this) {
    AdRealEstateType.NONE -> null
    AdRealEstateType.APARTMENT -> RepoAdRealEstateType.APARTMENT
    AdRealEstateType.HOUSE -> RepoAdRealEstateType.HOUSE
    AdRealEstateType.TOWNHOUSE -> RepoAdRealEstateType.TOWNHOUSE
    AdRealEstateType.DACHA -> RepoAdRealEstateType.DACHA
    AdRealEstateType.OTHER -> RepoAdRealEstateType.OTHER
}

