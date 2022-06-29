package com.rorono.a22recycler.utils

import kotlin.math.roundToInt


object Rounding { //исправить
    fun getTwoNumbersAfterDecimalPoint(value: Double): Double = (value * 100).roundToInt() / 100.0
}