package ru.otus.kotlin.walls.auth

import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.permissions.PrincipalRelations
import ru.otus.kotlin.walls.common.permissions.UserPermissions

fun checkPermitted(
    command: AdCommand,
    relations: Iterable<PrincipalRelations>,
    permissions: Iterable<UserPermissions>,
) =
    relations.asSequence().flatMap { relation ->
        permissions.map { permission ->
            AccessTableConditions(
                command = command,
                permission = permission,
                relation = relation,
            )
        }
    }.any {
        accessTable[it] == true
    }

private data class AccessTableConditions(
    val command: AdCommand,
    val permission: UserPermissions,
    val relation: PrincipalRelations
)

private val accessTable = mapOf(
    // Create
    AccessTableConditions(
        command = AdCommand.CREATE,
        permission = UserPermissions.CREATE_OWN,
        relation = PrincipalRelations.NEW,
    ) to true,

    // Read
    AccessTableConditions(
        command = AdCommand.READ,
        permission = UserPermissions.READ_OWN,
        relation = PrincipalRelations.OWN,
    ) to true,
    AccessTableConditions(
        command = AdCommand.READ,
        permission = UserPermissions.READ_PUBLIC,
        relation = PrincipalRelations.PUBLIC,
    ) to true,
    AccessTableConditions(
        command = AdCommand.READ,
        permission = UserPermissions.READ_PUBLIC,
        relation = PrincipalRelations.OWN,
    ) to true,
    AccessTableConditions(
        command = AdCommand.READ,
        permission = UserPermissions.READ_PUBLIC,
        relation = PrincipalRelations.MODERATABLE,
    ) to true,
    AccessTableConditions(
        command = AdCommand.READ,
        permission = UserPermissions.READ_CANDIDATE,
        relation = PrincipalRelations.MODERATABLE,
    ) to true,

    // Update
    AccessTableConditions(
        command = AdCommand.UPDATE,
        permission = UserPermissions.UPDATE_OWN,
        relation = PrincipalRelations.OWN,
    ) to true,
    AccessTableConditions(
        command = AdCommand.UPDATE,
        permission = UserPermissions.UPDATE_CANDIDATE,
        relation = PrincipalRelations.OWN,
    ) to true,
    AccessTableConditions(
        command = AdCommand.UPDATE,
        permission = UserPermissions.UPDATE_CANDIDATE,
        relation = PrincipalRelations.MODERATABLE,
    ) to true,

    // Delete
    AccessTableConditions(
        command = AdCommand.DELETE,
        permission = UserPermissions.DELETE_OWN,
        relation = PrincipalRelations.OWN,
    ) to true,
    AccessTableConditions(
        command = AdCommand.DELETE,
        permission = UserPermissions.DELETE_CANDIDATE,
        relation = PrincipalRelations.OWN,
    ) to true,
    AccessTableConditions(
        command = AdCommand.DELETE,
        permission = UserPermissions.DELETE_CANDIDATE,
        relation = PrincipalRelations.MODERATABLE,
    ) to true,

    // Search
    AccessTableConditions(
        command = AdCommand.SEARCH,
        permission = UserPermissions.SEARCH_OWN,
        relation = PrincipalRelations.OWN,
    ) to true,
    AccessTableConditions(
        command = AdCommand.SEARCH,
        permission = UserPermissions.SEARCH_PUBLIC,
        relation = PrincipalRelations.PUBLIC,
    ) to true,
    AccessTableConditions(
        command = AdCommand.SEARCH,
        permission = UserPermissions.SEARCH_PUBLIC,
        relation = PrincipalRelations.MODERATABLE,
    ) to true,
    AccessTableConditions(
        command = AdCommand.SEARCH,
        permission = UserPermissions.SEARCH_PUBLIC,
        relation = PrincipalRelations.OWN,
    ) to true,
)
