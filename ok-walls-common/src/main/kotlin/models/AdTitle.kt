package ru.otus.kotlin.walls.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class AdTitle(val value: String) {
    companion object {
        val NONE = AdTitle("")
    }
}
