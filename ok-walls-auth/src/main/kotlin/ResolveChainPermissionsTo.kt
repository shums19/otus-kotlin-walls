package ru.otus.kotlin.walls.auth

import ru.otus.kotlin.walls.common.permissions.UserGroups
import ru.otus.kotlin.walls.common.permissions.UserPermissions

fun resolveChainPermissions(
    groups: Iterable<UserGroups>,
) = mutableSetOf<UserPermissions>()
    .apply {
        addAll(groups.flatMap { groupPermissionsAdmits[it] ?: emptySet() })
        removeAll(groups.flatMap { groupPermissionsDenys[it] ?: emptySet() }.toSet())
    }
    .toSet()

private val groupPermissionsAdmits = mapOf(
    UserGroups.USER to setOf(
        UserPermissions.READ_OWN,
        UserPermissions.READ_PUBLIC,
        UserPermissions.CREATE_OWN,
        UserPermissions.UPDATE_OWN,
        UserPermissions.DELETE_OWN,
        UserPermissions.SEARCH_OWN,
        UserPermissions.SEARCH_PUBLIC,
    ),
    UserGroups.MODERATOR to setOf(
        UserPermissions.CREATE_OWN,

        UserPermissions.READ_OWN,
        UserPermissions.READ_PUBLIC,
        UserPermissions.READ_CANDIDATE,

        UserPermissions.UPDATE_OWN,
        UserPermissions.UPDATE_CANDIDATE,

        UserPermissions.DELETE_OWN,
        UserPermissions.DELETE_CANDIDATE,

        UserPermissions.SEARCH_OWN,
        UserPermissions.SEARCH_PUBLIC,
    ),
    UserGroups.ADMIN to setOf(
        UserPermissions.CREATE_OWN,

        UserPermissions.READ_OWN,
        UserPermissions.READ_PUBLIC,
        UserPermissions.READ_CANDIDATE,

        UserPermissions.UPDATE_OWN,
        UserPermissions.UPDATE_CANDIDATE,

        UserPermissions.DELETE_OWN,
        UserPermissions.DELETE_CANDIDATE,

        UserPermissions.SEARCH_OWN,
        UserPermissions.SEARCH_PUBLIC,
    ),
    UserGroups.TEST to setOf(
        UserPermissions.READ_OWN,
        UserPermissions.READ_PUBLIC,
        UserPermissions.CREATE_OWN,
        UserPermissions.UPDATE_OWN,
        UserPermissions.DELETE_OWN,
        UserPermissions.SEARCH_OWN,
        UserPermissions.SEARCH_PUBLIC,
    ),
    UserGroups.BAN_AD to setOf(),
)

private val groupPermissionsDenys = mapOf(
    UserGroups.USER to setOf(),
    UserGroups.MODERATOR to setOf(),
    UserGroups.ADMIN to setOf(),
    UserGroups.TEST to setOf(),
    UserGroups.BAN_AD to setOf(
        UserPermissions.CREATE_OWN,
        UserPermissions.READ_CANDIDATE,
        UserPermissions.UPDATE_OWN,
        UserPermissions.UPDATE_CANDIDATE,
        UserPermissions.DELETE_OWN,
        UserPermissions.DELETE_CANDIDATE,
    ),
)
