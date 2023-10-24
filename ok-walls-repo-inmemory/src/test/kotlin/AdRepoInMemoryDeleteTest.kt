package ru.otus.kotlin.walls.repo.inmemory

import ru.otus.kotlin.walls.repo.tests.RepoAdDeleteTest
import ru.otus.kotlin.walls.common.repo.IAdRepository

class AdRepoInMemoryDeleteTest : RepoAdDeleteTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
