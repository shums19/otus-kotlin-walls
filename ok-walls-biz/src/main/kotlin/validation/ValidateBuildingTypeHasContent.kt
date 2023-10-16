package ru.otus.kotlin.walls.biz.validation

import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker
import ru.otus.kotlin.walls.common.helpers.errorValidation
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.helpers.fail
import ru.otus.kotlin.walls.common.models.AdBuildingType
import ru.otus.kotlin.walls.common.models.AdRealEstateType

fun ICorChainDsl<AdContext>.validateBuildingTypeHasContent(title: String) = worker {
    this.title = title

    on { adValidating.buildingType == AdBuildingType.NONE }
    handle {
        fail(
            errorValidation(
                field = "buildingType",
                violationCode = "no-content",
                description = "field must contain value"
            )
        )
    }
}
