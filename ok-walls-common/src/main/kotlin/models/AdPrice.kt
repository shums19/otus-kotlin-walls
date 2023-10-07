package ru.otus.kotlin.walls.common.models

import java.math.BigDecimal
import kotlin.jvm.JvmInline

@JvmInline
value class AdPrice(val value: BigDecimal) {
    companion object {
        val NONE = AdPrice(0.toBigDecimal())
    }
}
