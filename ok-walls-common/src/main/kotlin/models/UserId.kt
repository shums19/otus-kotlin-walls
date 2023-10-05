package ru.otus.kotlin.walls.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class UserId(val value: String) {
    companion object {
        val NONE = UserId("")
    }
}
