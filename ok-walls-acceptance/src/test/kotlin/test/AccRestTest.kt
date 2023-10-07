package ru.otus.kotlin.walls.blackbox.test

import io.kotest.core.annotation.Ignored
import ru.otus.kotlin.walls.blackbox.docker.WiremockDockerCompose
import ru.otus.kotlin.walls.blackbox.fixture.BaseFunSpec
import ru.otus.kotlin.walls.blackbox.fixture.client.RestClient
import ru.otus.kotlin.walls.blackbox.fixture.docker.DockerCompose

@Ignored
open class AccRestTestBase(dockerCompose: DockerCompose) : BaseFunSpec(dockerCompose, {
    val client = RestClient(dockerCompose)

    testApiV1(client)
    testApiV2(client)
})
class AccRestWiremockTest : AccRestTestBase(WiremockDockerCompose)
// TODO class AccRestSpringTest : AccRestTestBase(SpringDockerCompose)
// TODO class AccRestKtorTest : AccRestTestBase(KtorDockerCompose)
