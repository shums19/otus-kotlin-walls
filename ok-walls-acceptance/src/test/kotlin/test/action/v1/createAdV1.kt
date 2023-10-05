package ru.otus.otuskotlin.walls.blackbox.test.action.v1

import io.kotest.assertions.withClue
import io.kotest.matchers.should
import ru.otus.otuskotlin.walls.blackbox.fixture.client.Client

suspend fun Client.createAd(): Unit =
    withClue("createAdV1") {
        val response = sendAndReceive(
            "ad/create", """
                {
                    "name": "Bolt"
                }
            """.trimIndent()
        )

        response should haveNoErrors
    }
