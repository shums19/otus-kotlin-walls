package ru.otus.kotlin.walls.repo.inmemory

import ru.otus.kotlin.walls.repo.tests.RepoAdCreateTest

class AdRepoInMemoryCreateTest : RepoAdCreateTest() {
    override val repo = AdRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.value }
    )
}
