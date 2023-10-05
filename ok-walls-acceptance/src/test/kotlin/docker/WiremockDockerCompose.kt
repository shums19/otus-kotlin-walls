package ru.otus.kotlin.walls.blackbox.docker

import ru.otus.kotlin.walls.blackbox.fixture.docker.AbstractDockerCompose

object WiremockDockerCompose : AbstractDockerCompose(
    "app-wiremock_1", 8080, "wiremock/docker-compose-wiremock.yml"
)
