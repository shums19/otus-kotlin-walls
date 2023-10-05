package ru.otus.kotlin.walls.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class AdSearchString(val value: String) {
    companion object {
        val NONE = AdSearchString("")
    }
}
