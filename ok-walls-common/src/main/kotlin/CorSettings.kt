package ru.otus.kotlin.walls.common

import ru.otus.kotlin.walls.common.repo.IAdRepository

data class CorSettings(
    val repoTest: IAdRepository = IAdRepository.NONE,
    val repoProd: IAdRepository = IAdRepository.NONE,
) {
    companion object {
        val NONE = CorSettings()
    }
}
