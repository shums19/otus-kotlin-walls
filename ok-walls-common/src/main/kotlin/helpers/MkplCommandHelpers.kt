package ru.otus.kotlin.walls.common.helpers

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdCommand

fun AdContext.isUpdatableCommand() =
    this.command in listOf(AdCommand.CREATE, AdCommand.UPDATE, AdCommand.DELETE)
