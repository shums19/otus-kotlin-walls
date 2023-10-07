package ru.otus.kotlin.walls.common.models

@JvmInline
value class AdRoomsNumber(val value: Int) {
    companion object {
        val NONE = AdRoomsNumber(0)
    }
}
