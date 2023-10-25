package ru.otus.kotlin.walls.auth

import ru.otus.kotlin.walls.common.models.AdPermissionClient
import ru.otus.kotlin.walls.common.permissions.PrincipalRelations
import ru.otus.kotlin.walls.common.permissions.UserPermissions

fun resolveFrontPermissions(
    permissions: Iterable<UserPermissions>,
    relations: Iterable<PrincipalRelations>,
) = mutableSetOf<AdPermissionClient>()
    .apply {
        for (permission in permissions) {
            for (relation in relations) {
                accessTable[permission]?.get(relation)?.let { this@apply.add(it) }
            }
        }
    }
    .toSet()

private val accessTable = mapOf(
    // READ
    UserPermissions.READ_OWN to mapOf(
        PrincipalRelations.OWN to AdPermissionClient.READ
    ),
    UserPermissions.READ_PUBLIC to mapOf(
        PrincipalRelations.PUBLIC to AdPermissionClient.READ
    ),
    UserPermissions.READ_CANDIDATE to mapOf(
        PrincipalRelations.MODERATABLE to AdPermissionClient.READ
    ),

    // UPDATE
    UserPermissions.UPDATE_OWN to mapOf(
        PrincipalRelations.OWN to AdPermissionClient.UPDATE
    ),
    UserPermissions.UPDATE_CANDIDATE to mapOf(
        PrincipalRelations.MODERATABLE to AdPermissionClient.UPDATE
    ),

    // DELETE
    UserPermissions.DELETE_OWN to mapOf(
        PrincipalRelations.OWN to AdPermissionClient.DELETE
    ),
    UserPermissions.DELETE_CANDIDATE to mapOf(
        PrincipalRelations.MODERATABLE to AdPermissionClient.DELETE
    ),
)
