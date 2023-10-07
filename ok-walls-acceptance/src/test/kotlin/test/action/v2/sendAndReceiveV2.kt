package ru.otus.kotlin.walls.blackbox.test.action.v2

import mu.KotlinLogging
import ru.otus.kotlin.walls.blackbox.fixture.client.Client

private val log = KotlinLogging.logger {}

suspend fun Client.sendAndReceive(path: String, requestBody: String): String {
    log.info { "Send to v2/$path\n$requestBody" }

    val responseBody = sendAndReceive("v2", path, requestBody)
    log.info { "Received\n$responseBody" }

    return responseBody
}
