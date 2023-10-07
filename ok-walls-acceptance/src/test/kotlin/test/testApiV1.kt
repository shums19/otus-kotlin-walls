package ru.otus.kotlin.walls.blackbox.test

import io.kotest.core.spec.style.FunSpec
import ru.otus.kotlin.walls.blackbox.fixture.client.Client
import ru.otus.kotlin.walls.blackbox.test.action.v1.createAd
import ru.otus.kotlin.walls.blackbox.test.action.v1.readAd

fun FunSpec.testApiV1(client: Client) {
    context("v1") {
        test("Create Ad ok") {
            client.createAd()
        }

        test("Read Ad ok") {
            client.readAd()
        }
    }
}
