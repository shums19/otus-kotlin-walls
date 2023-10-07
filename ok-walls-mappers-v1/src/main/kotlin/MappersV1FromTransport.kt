package ru.otus.kotlin.walls.mappers.v1

import ru.otus.kotlin.walls.api.v1.models.AdCreateObject
import ru.otus.kotlin.walls.api.v1.models.AdCreateRequest
import ru.otus.kotlin.walls.api.v1.models.AdDebug
import ru.otus.kotlin.walls.api.v1.models.AdDeleteRequest
import ru.otus.kotlin.walls.api.v1.models.AdReadRequest
import ru.otus.kotlin.walls.api.v1.models.AdRequestDebugMode
import ru.otus.kotlin.walls.api.v1.models.AdRequestDebugStub
import ru.otus.kotlin.walls.api.v1.models.AdSearchFilter
import ru.otus.kotlin.walls.api.v1.models.AdSearchRequest
import ru.otus.kotlin.walls.api.v1.models.AdUpdateObject
import ru.otus.kotlin.walls.api.v1.models.AdUpdateRequest
import ru.otus.kotlin.walls.api.v1.models.BuildingType
import ru.otus.kotlin.walls.api.v1.models.IRequest
import ru.otus.kotlin.walls.api.v1.models.Status
import ru.otus.kotlin.walls.api.v1.models.Type
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdArea
import ru.otus.kotlin.walls.common.models.AdBuildingType
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdDescription
import ru.otus.kotlin.walls.common.models.AdFilter
import ru.otus.kotlin.walls.common.models.AdFloor
import ru.otus.kotlin.walls.common.models.AdId
import ru.otus.kotlin.walls.common.models.AdPrice
import ru.otus.kotlin.walls.common.models.AdRealEstateStatus
import ru.otus.kotlin.walls.common.models.AdRealEstateType
import ru.otus.kotlin.walls.common.models.AdRoomsNumber
import ru.otus.kotlin.walls.common.models.AdSearchString
import ru.otus.kotlin.walls.common.models.AdTitle
import ru.otus.kotlin.walls.common.models.RequestId
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.common.stubs.AdStubCase
import ru.otus.kotlin.walls.mappers.v1.exceptions.UnknownRequestClass

fun AdContext.fromTransport(request: IRequest) = when (request) {
    is AdCreateRequest -> fromTransport(request)
    is AdReadRequest -> fromTransport(request)
    is AdUpdateRequest -> fromTransport(request)
    is AdDeleteRequest -> fromTransport(request)
    is AdSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toAdId() = this?.let { AdId(it) } ?: AdId.NONE

private fun String?.toAdWithId() = Ad(id = this.toAdId())

private fun IRequest?.requestId() = this?.requestId?.let { RequestId(it) } ?: RequestId.NONE

private fun String?.toAdTitle() = this?.let { AdTitle(it) } ?: AdTitle.NONE

private fun String?.toAdDescription() = this?.let { AdDescription(it) } ?: AdDescription.NONE

private fun AdDebug?.toWorkMode(): WorkMode = when (this?.mode) {
    null, AdRequestDebugMode.PROD -> WorkMode.PROD
    AdRequestDebugMode.TEST -> WorkMode.TEST
    AdRequestDebugMode.STUB -> WorkMode.STUB
}

private fun AdDebug?.toStubCase(): AdStubCase = when (this?.stub) {
    AdRequestDebugStub.SUCCESS -> AdStubCase.SUCCESS
    AdRequestDebugStub.NOT_FOUND -> AdStubCase.NOT_FOUND
    AdRequestDebugStub.BAD_ID -> AdStubCase.BAD_ID
    AdRequestDebugStub.BAD_TITLE -> AdStubCase.BAD_TITLE
    AdRequestDebugStub.BAD_DESCRIPTION -> AdStubCase.BAD_DESCRIPTION
    AdRequestDebugStub.BAD_AREA -> AdStubCase.BAD_AREA
    AdRequestDebugStub.BAD_PRICE -> AdStubCase.BAD_PRICE
    AdRequestDebugStub.BAD_ROOMS_NUMBER -> AdStubCase.BAD_ROOMS_NUMBER
    AdRequestDebugStub.BAD_FLOOR -> AdStubCase.BAD_FLOOR
    AdRequestDebugStub.CANNOT_DELETE -> AdStubCase.CANNOT_DELETE
    AdRequestDebugStub.BAD_SEARCH_STRING -> AdStubCase.BAD_SEARCH_STRING
    null -> AdStubCase.NONE
}

fun AdContext.fromTransport(request: AdCreateRequest) {
    command = AdCommand.CREATE
    requestId = request.requestId()
    adRequest = request.ad?.toInternal() ?: Ad()
    workMode = request.debug.toWorkMode()
    stubCase = request.debug.toStubCase()
}

fun AdContext.fromTransport(request: AdReadRequest) {
    command = AdCommand.READ
    requestId = request.requestId()
    adRequest = request.ad?.id.toAdWithId()
    workMode = request.debug.toWorkMode()
    stubCase = request.debug.toStubCase()
}

fun AdContext.fromTransport(request: AdUpdateRequest) {
    command = AdCommand.UPDATE
    requestId = request.requestId()
    adRequest = request.ad?.toInternal() ?: Ad()
    workMode = request.debug.toWorkMode()
    stubCase = request.debug.toStubCase()
}

fun AdContext.fromTransport(request: AdDeleteRequest) {
    command = AdCommand.DELETE
    requestId = request.requestId()
    adRequest = request.ad?.id.toAdWithId()
    workMode = request.debug.toWorkMode()
    stubCase = request.debug.toStubCase()
}

fun AdContext.fromTransport(request: AdSearchRequest) {
    command = AdCommand.SEARCH
    requestId = request.requestId()
    adFilterRequest = request.adFilter.toInternal()
    workMode = request.debug.toWorkMode()
    stubCase = request.debug.toStubCase()
}

private fun AdSearchFilter?.toInternal(): AdFilter = AdFilter(
    searchString = this?.searchString?.let { AdSearchString(it) } ?: AdSearchString.NONE,
)

private fun AdCreateObject.toInternal(): Ad = Ad(
    title = this.title.toAdTitle(),
    description = this.description.toAdDescription(),
    isActive = this.active ?: false,
    area = this.area?.let { AdArea(it) } ?: AdArea.NONE,
    price = this.price?.let { AdPrice(it) } ?: AdPrice.NONE,
    roomsNumber = this.roomsNumber?.let { AdRoomsNumber(it) } ?: AdRoomsNumber.NONE,
    floor = this.floor?.let { AdFloor(it) } ?: AdFloor.NONE,
    hasLift = this.hasLift ?: false,
    type = this.type.toInternal(),
    status = this.status.toInternal(),
    buildingType = this.buildingType.toInternal(),
)

private fun AdUpdateObject.toInternal(): Ad = Ad(
    id = this.id.toAdId(),
    title = this.title.toAdTitle(),
    description = this.description.toAdDescription(),
    isActive = this.active ?: false,
    area = this.area?.let { AdArea(it) } ?: AdArea.NONE,
    price = this.price?.let { AdPrice(it) } ?: AdPrice.NONE,
    roomsNumber = this.roomsNumber?.let { AdRoomsNumber(it) } ?: AdRoomsNumber.NONE,
    floor = this.floor?.let { AdFloor(it) } ?: AdFloor.NONE,
    hasLift = this.hasLift ?: false,
    type = this.type.toInternal(),
    status = this.status.toInternal(),
    buildingType = this.buildingType.toInternal(),
)

private fun Type?.toInternal(): AdRealEstateType =
    when (this) {
        Type.APARTMENT -> AdRealEstateType.APARTMENT
        Type.HOUSE -> AdRealEstateType.HOUSE
        Type.TOWNHOUSE -> AdRealEstateType.TOWNHOUSE
        Type.DACHA -> AdRealEstateType.DACHA
        Type.OTHER -> AdRealEstateType.OTHER
        null -> AdRealEstateType.NONE
    }

private fun Status?.toInternal(): AdRealEstateStatus =
    when (this) {
        Status.CONSTRUCTION -> AdRealEstateStatus.CONSTRUCTION
        Status.NEW -> AdRealEstateStatus.NEW
        Status.RESALE -> AdRealEstateStatus.RESALE
        null -> AdRealEstateStatus.NONE
    }

private fun BuildingType?.toInternal(): AdBuildingType =
    when (this) {
        BuildingType.BRICK -> AdBuildingType.BRICK
        BuildingType.PANEL -> AdBuildingType.PANEL
        BuildingType.МONOLITHIC -> AdBuildingType.МONOLITHIC
        BuildingType.BLOCK -> AdBuildingType.BLOCK
        BuildingType.WOOD -> AdBuildingType.WOOD
        BuildingType.OTHER -> AdBuildingType.OTHER
        null -> AdBuildingType.NONE
    }

