package ru.otus.kotlin.walls.repo.inmemory

import ru.otus.kotlin.walls.repo.tests.RepoAdUpdateTest
import ru.otus.kotlin.walls.common.repo.IAdRepository

class AdRepoInMemoryUpdateTest : RepoAdUpdateTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.value }
    )
}
