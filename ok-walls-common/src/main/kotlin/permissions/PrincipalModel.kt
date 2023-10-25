package ru.otus.kotlin.walls.common.permissions

import ru.otus.kotlin.walls.common.models.UserId

data class PrincipalModel(
    val id: UserId = UserId.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: Set<UserGroups> = emptySet()
) {
    companion object {
        val NONE = PrincipalModel()
    }
}
