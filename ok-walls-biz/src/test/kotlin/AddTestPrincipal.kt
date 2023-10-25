package ru.otus.kotlin.walls.biz

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.UserId
import ru.otus.kotlin.walls.common.permissions.PrincipalModel
import ru.otus.kotlin.walls.common.permissions.UserGroups

fun AdContext.addTestPrincipal(userId: UserId = UserId("321")) {
    principal = PrincipalModel(
        id = userId,
        groups = setOf(
            UserGroups.USER,
            UserGroups.TEST,
        )
    )
}
