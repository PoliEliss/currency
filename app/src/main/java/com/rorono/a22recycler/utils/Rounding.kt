package com.rorono.a22recycler.utils




object Rounding {
    fun getTwoNumbersAfterDecimalPoint(value: Double): Double = kotlin.math.floor(value * 100) / 100
}