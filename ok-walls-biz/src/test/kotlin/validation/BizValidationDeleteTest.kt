package ru.otus.kotlin.walls.biz.validation

import io.kotest.core.spec.style.FreeSpec
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.models.AdCommand

class BizValidationDeleteTest : FreeSpec({
    val command = AdCommand.DELETE

    val processor = AdProcessor()

    "correct id" { validationIdCorrect(command, processor) }
    "trim id" { validationIdTrim(command, processor) }
    "empty id" { validationIdEmpty(command, processor) }
    "bad format id" { validationIdFormat(command, processor) }
})

