package ru.otus.kotlin.walls.repo.cassandra.model

import ru.otus.kotlin.walls.common.models.AdRealEstateStatus

enum class RepoAdRealEstateStatus {
    CONSTRUCTION,
    NEW,
    RESALE,
}

fun RepoAdRealEstateStatus?.fromTransport(): AdRealEstateStatus = when(this) {
    null -> AdRealEstateStatus.NONE
    RepoAdRealEstateStatus.CONSTRUCTION -> AdRealEstateStatus.CONSTRUCTION
    RepoAdRealEstateStatus.NEW -> AdRealEstateStatus.NEW
    RepoAdRealEstateStatus.RESALE -> AdRealEstateStatus.RESALE
}

fun AdRealEstateStatus.toTransport(): RepoAdRealEstateStatus? = when(this) {
    AdRealEstateStatus.NONE -> null
    AdRealEstateStatus.CONSTRUCTION -> RepoAdRealEstateStatus.CONSTRUCTION
    AdRealEstateStatus.NEW -> RepoAdRealEstateStatus.NEW
    AdRealEstateStatus.RESALE -> RepoAdRealEstateStatus.RESALE
}
