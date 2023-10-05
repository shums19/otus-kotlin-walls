package ru.otus.kotlin.walls.common

import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdFilter
import ru.otus.kotlin.walls.common.models.RequestId
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.common.stubs.AdStub
import java.time.OffsetDateTime

data class AdContext(
    var command: AdCommand = AdCommand.NONE,
    var state: State = State.NONE,
    val errors: MutableList<AdError> = mutableListOf(),

    var workMode: WorkMode = WorkMode.PROD,
    var stubCase: AdStub = AdStub.NONE,

    var requestId: RequestId = RequestId.NONE,
    var timeStart: OffsetDateTime = OffsetDateTime.MIN,
    var adRequest: Ad = Ad(),
    var adFilterRequest: AdFilter = AdFilter(),
    var adResponse: Ad = Ad(),
    var adsResponse: MutableList<Ad> = mutableListOf(),
)
