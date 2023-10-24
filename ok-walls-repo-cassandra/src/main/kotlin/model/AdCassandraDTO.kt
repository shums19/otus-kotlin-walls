package ru.otus.kotlin.walls.repo.cassandra.model

import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import ru.otus.kotlin.walls.common.models.*

@Entity
data class AdCassandraDTO(
    @field:CqlName(COLUMN_ID)
    @field:PartitionKey // можно задать порядок
    var id: String? = null,
    @field:CqlName(COLUMN_TITLE)
    var title: String? = null,
    @field:CqlName(COLUMN_DESCRIPTION)
    var description: String? = null,
    @field:CqlName(COLUMN_OWNER_ID)
    var ownerId: String? = null,
    @field:CqlName(COLUMN_IS_ACTIVE)
    var isActive: Boolean? = null,
    @field:CqlName(COLUMN_HAS_LIFT)
    var hasLift: Boolean? = null,
    @field:CqlName(COLUMN_PRICE)
    var price: Double? = null,
    @field:CqlName(COLUMN_AREA)
    var area: Double? = null,
    @field:CqlName(COLUMN_FLOOR)
    var floor: Int? = null,
    @field:CqlName(COLUMN_ROOMS_NUMBER)
    var roomsNumber: Int? = null,
    @field:CqlName(COLUMN_TYPE)
    var type: RepoAdRealEstateType? = null,
    @field:CqlName(COLUMN_STATUS)
    var status: RepoAdRealEstateStatus? = null,
    @field:CqlName(COLUMN_BUILDING_TYPE)
    var buildingType: RepoAdBuildingType? = null,
    @field:CqlName(COLUMN_LOCK)
    var lock: String?
) {
    constructor(adModel: Ad) : this(
        ownerId = adModel.ownerId.value.takeIf { it.isNotBlank() },
        id = adModel.id.value.takeIf { it.isNotBlank() },
        title = adModel.title.value.takeIf { it.isNotBlank() },
        description = adModel.description.value.takeIf { it.isNotBlank() },
        isActive = adModel.isActive,
        hasLift = adModel.hasLift,
        price = adModel.price.takeIf { it != AdPrice.NONE }?.value?.toDouble(),
        area = adModel.area.takeIf { it != AdArea.NONE }?.value?.toDouble(),
        floor = adModel.floor.takeIf { it != AdFloor.NONE }?.value,
        roomsNumber = adModel.roomsNumber.takeIf { it != AdRoomsNumber.NONE }?.value,
        type = adModel.type.toTransport(),
        status = adModel.status.toTransport(),
        buildingType = adModel.buildingType.toTransport(),
        lock = adModel.lock.value.takeIf { it.isNotBlank() },
    )

    fun toAdModel(): Ad =
        Ad(
            ownerId = ownerId?.let { UserId(it) } ?: UserId.NONE,
            id = id?.let { AdId(it) } ?: AdId.NONE,
            title = title?.let { AdTitle(it) } ?: AdTitle.NONE,
            description = description?.let { AdDescription(it) } ?: AdDescription.NONE,
            isActive = isActive ?: false,
            hasLift = hasLift ?: false,
            price = price?.let { AdPrice(it.toBigDecimal()) } ?: AdPrice.NONE,
            area = area?.let { AdArea(it.toBigDecimal()) } ?: AdArea.NONE,
            floor = floor?.let { AdFloor(it) } ?: AdFloor.NONE,
            roomsNumber = roomsNumber?.let { AdRoomsNumber(it) } ?: AdRoomsNumber.NONE,
            type = type.fromTransport(),
            status = status.fromTransport(),
            buildingType = buildingType.fromTransport(),
            lock = lock?.let { AdLock(it) } ?: AdLock.NONE,
        )

    companion object {
        const val TABLE_NAME = "ads"

        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_OWNER_ID = "owner_id"
        const val COLUMN_IS_ACTIVE = "is_active"
        const val COLUMN_HAS_LIFT = "has_lift"
        const val COLUMN_PRICE = "price"
        const val COLUMN_AREA = "area"
        const val COLUMN_FLOOR = "floor"
        const val COLUMN_ROOMS_NUMBER = "rooms_number"
        const val COLUMN_TYPE = "type"
        const val COLUMN_STATUS = "status"
        const val COLUMN_BUILDING_TYPE = "building_type"
        const val COLUMN_LOCK = "lock"

        fun table(keyspace: String, tableName: String) =
            SchemaBuilder
                .createTable(keyspace, tableName)
                .ifNotExists()
                .withPartitionKey(COLUMN_ID, DataTypes.TEXT)
                .withColumn(COLUMN_TITLE, DataTypes.TEXT)
                .withColumn(COLUMN_DESCRIPTION, DataTypes.TEXT)
                .withColumn(COLUMN_OWNER_ID, DataTypes.TEXT)
                .withColumn(COLUMN_IS_ACTIVE, DataTypes.BOOLEAN)
                .withColumn(COLUMN_HAS_LIFT, DataTypes.BOOLEAN)
                .withColumn(COLUMN_PRICE, DataTypes.DOUBLE)
                .withColumn(COLUMN_AREA, DataTypes.DOUBLE)
                .withColumn(COLUMN_FLOOR, DataTypes.INT)
                .withColumn(COLUMN_ROOMS_NUMBER, DataTypes.INT)
                .withColumn(COLUMN_TYPE, DataTypes.TEXT)
                .withColumn(COLUMN_STATUS, DataTypes.TEXT)
                .withColumn(COLUMN_BUILDING_TYPE, DataTypes.TEXT)
                .withColumn(COLUMN_LOCK, DataTypes.TEXT)
                .build()

        fun titleIndex(keyspace: String, tableName: String, locale: String = "en") =
            SchemaBuilder
                .createIndex()
                .ifNotExists()
                .usingSASI()
                .onTable(keyspace, tableName)
                .andColumn(COLUMN_TITLE)
                .withSASIOptions(mapOf("mode" to "CONTAINS", "tokenization_locale" to locale))
                .build()
    }
}
