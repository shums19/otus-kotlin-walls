package ru.otus.kotlin.walls.mappers.v1.exceptions

import ru.otus.kotlin.walls.common.models.AdCommand

class UnknownAdCommand(command: AdCommand) : Throwable("Wrong command=$command at mapping toTransport stage")
