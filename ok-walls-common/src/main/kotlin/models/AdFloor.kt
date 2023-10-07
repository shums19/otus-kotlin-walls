package ru.otus.kotlin.walls.common.models

@JvmInline
value class AdFloor(val value: Int) {
    companion object {
        val NONE = AdFloor(Int.MIN_VALUE)
    }
}
