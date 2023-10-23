package ru.otus.kotlin.walls.common.models

data class Ad(
    var id: AdId = AdId.NONE,
    var title: AdTitle = AdTitle.NONE,
    var description: AdDescription = AdDescription.NONE,
    var ownerId: UserId = UserId.NONE,
    var isActive: Boolean = false,
    var type: AdRealEstateType = AdRealEstateType.NONE,
    var status: AdRealEstateStatus = AdRealEstateStatus.NONE,
    var buildingType: AdBuildingType = AdBuildingType.NONE,
    var hasLift: Boolean = false,
    var area: AdArea = AdArea.NONE,
    var price: AdPrice = AdPrice.NONE,
    var roomsNumber: AdRoomsNumber = AdRoomsNumber.NONE,
    var floor: AdFloor = AdFloor.NONE,
    val permissionsClient: MutableSet<AdPermissionClient> = mutableSetOf()
) {
    fun deepCopy(): Ad = this.copy(
        permissionsClient = permissionsClient.toMutableSet(),
    )
}
