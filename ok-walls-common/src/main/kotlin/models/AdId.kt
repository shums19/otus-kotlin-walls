package ru.otus.kotlin.walls.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class AdId(val value: String) {
    companion object {
        val NONE = AdId("")
    }
}
