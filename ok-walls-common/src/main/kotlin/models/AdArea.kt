package ru.otus.kotlin.walls.common.models

import java.math.BigDecimal
import kotlin.jvm.JvmInline

@JvmInline
value class AdArea(val value: BigDecimal) {
    companion object {
        val NONE = AdArea(0.toBigDecimal())
    }
}
