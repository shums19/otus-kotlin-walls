package ru.otus.kotlin.walls.repo.cassandra.model

import ru.otus.kotlin.walls.common.models.AdBuildingType

enum class RepoAdBuildingType{
    BRICK,
    PANEL,
    МONOLITHIC,
    BLOCK,
    WOOD,
    OTHER
}

fun RepoAdBuildingType?.fromTransport(): AdBuildingType = when(this) {
    null -> AdBuildingType.NONE
    RepoAdBuildingType.BRICK -> AdBuildingType.BRICK
    RepoAdBuildingType.PANEL -> AdBuildingType.PANEL
    RepoAdBuildingType.МONOLITHIC -> AdBuildingType.МONOLITHIC
    RepoAdBuildingType.BLOCK -> AdBuildingType.BLOCK
    RepoAdBuildingType.WOOD -> AdBuildingType.WOOD
    RepoAdBuildingType.OTHER -> AdBuildingType.OTHER
}

fun AdBuildingType.toTransport(): RepoAdBuildingType? = when(this) {
    AdBuildingType.NONE -> null
    AdBuildingType.BRICK -> RepoAdBuildingType.BRICK
    AdBuildingType.PANEL -> RepoAdBuildingType.PANEL
    AdBuildingType.МONOLITHIC -> RepoAdBuildingType.МONOLITHIC
    AdBuildingType.BLOCK -> RepoAdBuildingType.BLOCK
    AdBuildingType.WOOD -> RepoAdBuildingType.WOOD
    AdBuildingType.OTHER -> RepoAdBuildingType.OTHER
}

