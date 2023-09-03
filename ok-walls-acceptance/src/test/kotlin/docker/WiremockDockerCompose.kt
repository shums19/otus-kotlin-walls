package ru.otus.otuskotlin.walls.blackbox.docker

import ru.otus.otuskotlin.walls.blackbox.fixture.docker.AbstractDockerCompose

object WiremockDockerCompose : AbstractDockerCompose(
    "app-wiremock_1", 8080, "wiremock/docker-compose-wiremock.yml"
)
