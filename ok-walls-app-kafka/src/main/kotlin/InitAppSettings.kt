package ru.otus.kotlin.walls.app.kafka

import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.CorSettings
import ru.otus.kotlin.walls.repo.cassandra.AdRepoCassandra
import ru.otus.kotlin.walls.repo.inmemory.AdRepoInMemory

fun initAppSettings(): AppSettings {
    val corSettings = CorSettings(
        repoTest = AdRepoInMemory(),
        repoProd = AdRepoCassandra(keyspaceName = "walls"),
    )
    return AppSettings(
        processor = AdProcessor(corSettings),
    )
}
