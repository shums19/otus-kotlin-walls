package ru.otus.kotlin.walls.common.models

data class AdFilter(
    var searchString: AdSearchString = AdSearchString.NONE,
    var ownerId: UserId = UserId.NONE,
    var searchPermissions: MutableSet<SearchPermissions> = mutableSetOf(),
)
