package ru.otus.kotlin.walls.app.base

import io.ktor.server.auth.jwt.*
import ru.otus.kotlin.walls.app.AuthConfig.Companion.F_NAME_CLAIM
import ru.otus.kotlin.walls.app.AuthConfig.Companion.GROUPS_CLAIM
import ru.otus.kotlin.walls.app.AuthConfig.Companion.ID_CLAIM
import ru.otus.kotlin.walls.app.AuthConfig.Companion.L_NAME_CLAIM
import ru.otus.kotlin.walls.app.AuthConfig.Companion.M_NAME_CLAIM
import ru.otus.kotlin.walls.common.models.UserId
import ru.otus.kotlin.walls.common.permissions.PrincipalModel
import ru.otus.kotlin.walls.common.permissions.UserGroups

fun JWTPrincipal?.toModel() = this?.run {
    PrincipalModel(
        id = payload.getClaim(ID_CLAIM).asString()?.let { UserId(it) } ?: UserId.NONE,
        fname = payload.getClaim(F_NAME_CLAIM).asString() ?: "",
        mname = payload.getClaim(M_NAME_CLAIM).asString() ?: "",
        lname = payload.getClaim(L_NAME_CLAIM).asString() ?: "",
        groups = payload
            .getClaim(GROUPS_CLAIM)
            ?.asList(String::class.java)
            ?.mapNotNull {
                when(it) {
                    "USER" -> UserGroups.USER
                    "ADMIN" -> UserGroups.ADMIN
                    "MODERATOR" -> UserGroups.MODERATOR
                    "TEST" -> UserGroups.TEST
                    "BAN_AD" -> UserGroups.BAN_AD
                    else -> null
                }
            }?.toSet() ?: emptySet()
    )
} ?: PrincipalModel.NONE
