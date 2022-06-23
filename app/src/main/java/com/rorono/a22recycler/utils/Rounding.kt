package com.rorono.a22recycler.utils

import java.lang.Math.floor

object Rounding {
    fun getRounding(value: Double): Double = kotlin.math.floor(value * 100) / 100
}