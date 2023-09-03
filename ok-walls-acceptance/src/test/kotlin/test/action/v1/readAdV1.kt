package ru.otus.otuskotlin.walls.blackbox.test.action.v1

import io.kotest.assertions.withClue
import io.kotest.matchers.should
import ru.otus.otuskotlin.walls.blackbox.fixture.client.Client

suspend fun Client.readAd(): Unit =
    withClue("readAdV1") {
        val id = "id"
        val response = sendAndReceive(
            "ad/read", """
                {
                    "id": "$id"
                }
            """.trimIndent()
        )

        response should haveErrors
        response should haveId(id)
    }
