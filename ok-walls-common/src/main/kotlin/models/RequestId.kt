package ru.otus.kotlin.walls.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class RequestId(val value: String) {
    companion object {
        val NONE = RequestId("")
    }
}
