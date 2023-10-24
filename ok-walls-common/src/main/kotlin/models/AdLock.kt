package ru.otus.kotlin.walls.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class AdLock(val value: String) {
    companion object {
        val NONE = AdLock("")
    }
}
