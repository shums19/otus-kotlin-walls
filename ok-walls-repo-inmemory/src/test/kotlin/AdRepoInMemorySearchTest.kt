package ru.otus.kotlin.walls.repo.inmemory

import ru.otus.kotlin.walls.repo.tests.RepoAdSearchTest
import ru.otus.kotlin.walls.common.repo.IAdRepository
import ru.otus.kotlin.walls.repo.inmemory.AdRepoInMemory

class AdRepoInMemorySearchTest : RepoAdSearchTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
