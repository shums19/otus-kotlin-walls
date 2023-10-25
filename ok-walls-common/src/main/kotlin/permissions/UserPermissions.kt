package ru.otus.kotlin.walls.common.permissions

enum class UserPermissions {
    CREATE_OWN,

    READ_OWN,
    READ_PUBLIC,
    READ_CANDIDATE,

    UPDATE_OWN,
    UPDATE_CANDIDATE,

    DELETE_OWN,
    DELETE_CANDIDATE,

    SEARCH_OWN,
    SEARCH_PUBLIC,
}
