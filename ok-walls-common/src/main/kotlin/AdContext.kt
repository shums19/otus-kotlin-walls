package ru.otus.kotlin.walls.common

import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdFilter
import ru.otus.kotlin.walls.common.models.RequestId
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.common.stubs.AdStubCase
import ru.otus.kotlin.walls.common.repo.IAdRepository
import ru.otus.kotlin.walls.common.permissions.PrincipalModel
import ru.otus.kotlin.walls.common.permissions.UserPermissions
import java.time.OffsetDateTime

data class AdContext(
    var settings: CorSettings = CorSettings.NONE,
    var adRepo: IAdRepository = IAdRepository.NONE,

    var command: AdCommand = AdCommand.NONE,
    var state: State = State.NONE,
    val errors: MutableList<AdError> = mutableListOf(),

    var workMode: WorkMode = WorkMode.PROD,
    var stubCase: AdStubCase = AdStubCase.NONE,

    var requestId: RequestId = RequestId.NONE,
    var timeStart: OffsetDateTime = OffsetDateTime.MIN,

    var adRequest: Ad = Ad(),
    var adFilterRequest: AdFilter = AdFilter(),

    var adValidating: Ad = Ad(),
    var adFilterValidating: AdFilter = AdFilter(),

    var adValidated: Ad = Ad(),
    var adFilterValidated: AdFilter = AdFilter(),

    var adRepoRead: Ad = Ad(),
    var adRepoPrepare: Ad = Ad(),
    var adRepoDone: Ad = Ad(),
    var adsRepoDone: MutableList<Ad> = mutableListOf(),

    var adResponse: Ad = Ad(),
    var adsResponse: MutableList<Ad> = mutableListOf(),

    var principal: PrincipalModel = PrincipalModel.NONE,
    val permissionsChain: MutableSet<UserPermissions> = mutableSetOf(),
    var permitted: Boolean = false,
)
