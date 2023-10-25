package ru.otus.kotlin.walls.repo.inmemory.model

import ru.otus.kotlin.walls.common.models.*
import java.math.BigDecimal

data class AdEntity(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val ownerId: String? = null,
    val isActive: Boolean? = null,
    val hasLift: Boolean? = null,
    val price: BigDecimal? = null,
    val area: BigDecimal? = null,
    val floor: Int? = null,
    val roomsNumber: Int? = null,
    val type: String? = null,
    val status: String? = null,
    val buildingType: String? = null,
    val lock: String? = null,
) {
    constructor(model: Ad): this(
        id = model.id.value.takeIf { it.isNotBlank() },
        title = model.title.value.takeIf { it.isNotBlank() },
        description = model.description.value.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.value.takeIf { it.isNotBlank() },
        isActive = model.isActive,
        hasLift = model.hasLift,
        price = model.price.value.takeIf { it.signum() > 0 },
        area = model.area.value.takeIf { it.signum() > 0 },
        floor = model.floor.value.takeIf { it > 0 },
        roomsNumber = model.roomsNumber.value.takeIf { it > 0 },
        type = model.type.takeIf { it != AdRealEstateType.NONE }?.name,
        status = model.status.takeIf { it != AdRealEstateStatus.NONE }?.name,
        buildingType = model.buildingType.takeIf { it != AdBuildingType.NONE }?.name,
        lock = model.lock.value.takeIf { it.isNotBlank() }
    )

    fun toInternal() = Ad(
        id = id?.let { AdId(it) } ?: AdId.NONE,
        title = title?.let { AdTitle(it) } ?: AdTitle.NONE,
        description = description?.let { AdDescription(it) } ?: AdDescription.NONE,
        ownerId = ownerId?.let { UserId(it) } ?: UserId.NONE,
        isActive = isActive ?: false,
        hasLift = hasLift ?: false,
        price = price?.let { AdPrice(it) } ?: AdPrice.NONE,
        area = area?.let { AdArea(it) } ?: AdArea.NONE,
        floor = floor?.let { AdFloor(it) } ?: AdFloor.NONE,
        roomsNumber = roomsNumber?.let { AdRoomsNumber(it) } ?: AdRoomsNumber.NONE,
        type = type?.let { AdRealEstateType.valueOf(it) } ?: AdRealEstateType.NONE,
        status = status?.let { AdRealEstateStatus.valueOf(it) } ?: AdRealEstateStatus.NONE,
        buildingType = buildingType?.let { AdBuildingType.valueOf(it) } ?: AdBuildingType.NONE,
        lock = lock?.let { AdLock(it) } ?: AdLock.NONE,
    )
}
