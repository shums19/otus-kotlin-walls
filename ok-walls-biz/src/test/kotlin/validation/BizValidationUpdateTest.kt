package ru.otus.kotlin.walls.biz.validation

import io.kotest.core.spec.style.FreeSpec
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.CorSettings
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.repo.stubs.AdRepoStub

class BizValidationUpdateTest : FreeSpec({
    val command = AdCommand.UPDATE

    val processor = AdProcessor(settings = CorSettings(repoTest = AdRepoStub()))

    "correct id" { validationIdCorrect(command, processor) }
    "trim id" { validationIdTrim(command, processor) }
    "empty id" { validationIdEmpty(command, processor) }
    "bad format id" { validationIdFormat(command, processor) }

    "correct title" { validationTitleCorrect(command, processor) }
    "trim title" { validationTitleTrim(command, processor) }
    "empty title" { validationTitleEmpty(command, processor) }
    "bad format title" { validationTitleSymbols(command, processor) }

    "correct description" { validationDescriptionCorrect(command, processor) }
    "trim description" { validationDescriptionTrim(command, processor) }
    "empty description" { validationDescriptionEmpty(command, processor) }
    "bad format description" { validationDescriptionSymbols(command, processor) }

    "correct price" { validationPriceCorrect(command, processor) }
    "incorrect price" { validationPriceIncorrect(command, processor) }

    "correct area" { validationAreaCorrect(command, processor) }
    "incorrect area" { validationAreaIncorrect(command, processor) }

    "correct floor" { validationFloorCorrect(command, processor) }
    "incorrect floor" { validationFloorIncorrect(command, processor) }

    "correct rooms number" { validationRoomsNumberCorrect(command, processor) }
    "incorrect rooms number" { validationRoomsNumberIncorrect(command, processor) }

    "correct type" { validationTypeCorrect(command, processor) }
    "incorrect type" { validationTypeIncorrect(command, processor) }

    "correct status" { validationStatusCorrect(command, processor) }
    "incorrect status" { validationStatusIncorrect(command, processor) }

    "correct building type" { validationBuildingTypeCorrect(command, processor) }
    "incorrect building type" { validationBuildingTypeIncorrect(command, processor) }
})

