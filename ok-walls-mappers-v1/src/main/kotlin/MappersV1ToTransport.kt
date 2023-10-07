package ru.otus.kotlin.walls.mappers.v1

import ru.otus.kotlin.walls.api.v1.models.*
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.*
import ru.otus.kotlin.walls.mappers.v1.exceptions.UnknownAdCommand

fun AdContext.toTransport(): IResponse = when (val cmd = command) {
    AdCommand.CREATE -> toTransportCreate()
    AdCommand.READ -> toTransportRead()
    AdCommand.UPDATE -> toTransportUpdate()
    AdCommand.DELETE -> toTransportDelete()
    AdCommand.SEARCH -> toTransportSearch()
    AdCommand.NONE -> throw UnknownAdCommand(cmd)
}

fun AdContext.toTransportCreate() = AdCreateResponse(
    requestId = this.requestId.takeIf { it != RequestId.NONE }?.value,
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransport(),
)

fun AdContext.toTransportRead() = AdReadResponse(
    requestId = this.requestId.takeIf { it != RequestId.NONE }?.value,
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransport(),
)

fun AdContext.toTransportUpdate() = AdUpdateResponse(
    requestId = this.requestId.takeIf { it != RequestId.NONE }?.value,
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransport(),
)

fun AdContext.toTransportDelete() = AdDeleteResponse(
    requestId = this.requestId.takeIf { it != RequestId.NONE }?.value,
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransport(),
)

fun AdContext.toTransportSearch() = AdSearchResponse(
    requestId = this.requestId.takeIf { it != RequestId.NONE }?.value,
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    ads = adsResponse.toTransport(),
)

fun AdContext.toTransportInit() = AdInitResponse(
    requestId = this.requestId.takeIf { it != RequestId.NONE }?.value,
    result = if (errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
)

fun List<Ad>.toTransport(): List<AdResponseObject>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Ad.toTransport(): AdResponseObject = AdResponseObject(
    id = this.id.takeIf { it != AdId.NONE }?.value,
    ownerId = this.ownerId.takeIf { it != UserId.NONE }?.value,
    title = this.title.takeIf { it != AdTitle.NONE }?.value,
    description = this.description.takeIf { it != AdDescription.NONE }?.value,
    active = this.isActive,
    hasLift = this.hasLift,
    area = this.area.takeIf { it != AdArea.NONE }?.value,
    price = this.price.takeIf { it != AdPrice.NONE }?.value,
    roomsNumber = this.roomsNumber.takeIf { it != AdRoomsNumber.NONE }?.value,
    floor = this.floor.takeIf { it != AdFloor.NONE }?.value,
    type = this.type.toTransport(),
    status = this.status.toTransport(),
    buildingType = this.buildingType.toTransport(),
    permissions = this.permissionsClient.toTransport(),
)

private fun Set<AdPermissionClient>.toTransport(): Set<AdPermissions>? = this
    .map { it.toTransport() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun AdPermissionClient.toTransport() = when (this) {
    AdPermissionClient.READ -> AdPermissions.READ
    AdPermissionClient.UPDATE -> AdPermissions.UPDATE
    AdPermissionClient.MAKE_VISIBLE_OWNER -> AdPermissions.MAKE_VISIBLE_OWN
    AdPermissionClient.MAKE_VISIBLE_GROUP -> AdPermissions.MAKE_VISIBLE_GROUP
    AdPermissionClient.MAKE_VISIBLE_PUBLIC -> AdPermissions.MAKE_VISIBLE_PUBLIC
    AdPermissionClient.DELETE -> AdPermissions.DELETE
}

private fun List<AdError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun AdError.toTransport() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun AdRealEstateType.toTransport(): Type? =
    when (this) {
        AdRealEstateType.APARTMENT -> Type.APARTMENT
        AdRealEstateType.HOUSE -> Type.HOUSE
        AdRealEstateType.TOWNHOUSE -> Type.TOWNHOUSE
        AdRealEstateType.DACHA -> Type.DACHA
        AdRealEstateType.OTHER -> Type.OTHER
        AdRealEstateType.NONE -> null
    }

private fun AdRealEstateStatus.toTransport(): Status? =
    when (this) {
        AdRealEstateStatus.CONSTRUCTION -> Status.CONSTRUCTION
        AdRealEstateStatus.NEW -> Status.NEW
        AdRealEstateStatus.RESALE -> Status.RESALE
        AdRealEstateStatus.NONE -> null
    }

private fun AdBuildingType.toTransport(): BuildingType? =
    when (this) {
        AdBuildingType.BRICK -> BuildingType.BRICK
        AdBuildingType.PANEL -> BuildingType.PANEL
        AdBuildingType.МONOLITHIC -> BuildingType.МONOLITHIC
        AdBuildingType.BLOCK -> BuildingType.BLOCK
        AdBuildingType.WOOD -> BuildingType.WOOD
        AdBuildingType.OTHER -> BuildingType.OTHER
        AdBuildingType.NONE -> null
    }
