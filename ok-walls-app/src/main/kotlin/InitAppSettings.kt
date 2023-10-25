package ru.otus.kotlin.walls.app

import io.ktor.server.application.*
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.CorSettings
import ru.otus.kotlin.walls.repo.cassandra.AdRepoCassandra
import ru.otus.kotlin.walls.repo.inmemory.AdRepoInMemory

fun Application.initAppSettings(): AppSettings {
    val corSettings = CorSettings(
        repoTest = AdRepoInMemory(),
        repoProd = AdRepoCassandra(keyspaceName = "walls"),
    )
    return AppSettings(
        processor = AdProcessor(corSettings),
        auth = initAppAuth(),
    )
}

private fun Application.initAppAuth(): AuthConfig = AuthConfig(
    secret = environment.config.propertyOrNull("jwt.secret")?.getString() ?: "",
    issuer = environment.config.property("jwt.issuer").getString(),
    audience = environment.config.property("jwt.audience").getString(),
    realm = environment.config.property("jwt.realm").getString(),
    clientId = environment.config.property("jwt.clientId").getString(),
    certUrl = environment.config.propertyOrNull("jwt.certUrl")?.getString()?.takeIf { it.isNotBlank() },
)
