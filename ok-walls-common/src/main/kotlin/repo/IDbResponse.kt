package ru.otus.kotlin.walls.common.repo

import ru.otus.kotlin.walls.common.models.AdError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<AdError>
}
