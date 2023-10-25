package ru.otus.kotlin.walls.repo.inmemory

import ru.otus.kotlin.walls.repo.tests.RepoAdReadTest
import ru.otus.kotlin.walls.common.repo.IAdRepository

class AdRepoInMemoryReadTest: RepoAdReadTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
