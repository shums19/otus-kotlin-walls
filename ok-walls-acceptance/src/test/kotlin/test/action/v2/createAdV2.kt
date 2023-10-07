package ru.otus.kotlin.walls.blackbox.test.action.v2

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import ru.otus.kotlin.walls.blackbox.fixture.client.Client

suspend fun Client.createAd(): Unit =
    withClue("createAdV2") {
        val response = sendAndReceive(
            "ad/create", """
                {
                    "name": "Bolt"
                }
            """.trimIndent()
        )

        response should haveNoErrors
        response shouldEqualJson """
            {
                "id": "123456",
                "name": "Name name",
                "result": "SUCCESS"
            }
        """
    }
