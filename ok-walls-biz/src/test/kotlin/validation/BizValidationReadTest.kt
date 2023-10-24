package ru.otus.kotlin.walls.biz.validation

import io.kotest.core.spec.style.FreeSpec
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.CorSettings
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.repo.stubs.AdRepoStub

class BizValidationReadTest : FreeSpec({
    val command = AdCommand.READ

    val processor = AdProcessor(settings = CorSettings(repoTest = AdRepoStub()))

    "correct id" { validationIdCorrect(command, processor) }
    "trim id" { validationIdTrim(command, processor) }
    "empty id" { validationIdEmpty(command, processor) }
    "bad format id" { validationIdFormat(command, processor) }
})

