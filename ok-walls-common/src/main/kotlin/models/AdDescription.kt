package ru.otus.kotlin.walls.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class AdDescription(val value: String) {
    companion object {
        val NONE = AdDescription("")
    }
}
