package ru.otus.otuskotlin.walls.blackbox.test

import io.kotest.core.spec.style.FunSpec
import ru.otus.otuskotlin.walls.blackbox.fixture.client.Client
import ru.otus.otuskotlin.walls.blackbox.test.action.v2.createAd

fun FunSpec.testApiV2(client: Client) {
    context("v2") {
        test("Create Ad error") {
            client.createAd()
        }
    }
}
