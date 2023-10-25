package ru.otus.kotlin.walls.app.helpers

import ru.otus.kotlin.walls.app.AppSettings
import ru.otus.kotlin.walls.app.AuthConfig
import ru.otus.kotlin.walls.common.CorSettings
import ru.otus.kotlin.walls.common.repo.IAdRepository
import ru.otus.kotlin.walls.repo.inmemory.AdRepoInMemory

fun testSettings(repo: IAdRepository? = null) = AppSettings(
    corSettings = CorSettings(
        repoTest = repo ?: AdRepoInMemory(),
        repoProd = repo ?: AdRepoInMemory(),
    ),
    auth = AuthConfig.TEST,
)
