package ru.otus.kotlin.walls.biz.stub

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdBuildingType
import ru.otus.kotlin.walls.common.models.AdRealEstateStatus
import ru.otus.kotlin.walls.common.models.AdRealEstateType
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.stubs.AdStubCase
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker
import ru.otus.kotlin.walls.stubs.AdStub

fun ICorChainDsl<AdContext>.stubUpdateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == AdStubCase.SUCCESS && state == State.RUNNING }
    handle {
        state = State.FINISHING
        val stub = AdStub.prepareResult {
            adRequest.id.let { id ->
                id.value.takeIf { it.isNotBlank() }?.also { this.id = id }
            }
            adRequest.title.let { title ->
                title.value.takeIf { it.isNotBlank() }?.also { this.title = title }
            }
            adRequest.description.let { description ->
                description.value.takeIf { it.isNotBlank() }?.also { this.description = description }
            }
            adRequest.area.let { area ->
                area.value.takeIf { it.signum() > 0 }?.also { this.area = area }
            }
            adRequest.price.let { price ->
                price.value.takeIf { it.signum() > 0 }?.also { this.price = price }
            }
            adRequest.roomsNumber.let { roomsNumber ->
                roomsNumber.value.takeIf { it > 0 }?.also { this.roomsNumber = roomsNumber }
            }
            adRequest.floor.let { floor ->
                floor.value.takeIf { it > 0 }?.also { this.floor = floor }
            }
            adRequest.type.takeIf { it != AdRealEstateType.NONE }?.also { this.type = it }
            adRequest.status.takeIf { it != AdRealEstateStatus.NONE }?.also { this.status = it }
            adRequest.buildingType.takeIf { it != AdBuildingType.NONE }?.also { this.buildingType = it }
        }
        adResponse = stub
    }
}
