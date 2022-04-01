package com.rorono.a22recycler

data class AMD(
    val CharCode: String,
    val ID: String,
    val Name: String,
    val Nominal: Int,
    val NumCode: String,
    val Previous: Double,
    val Value: Double
) : GetValueCurrency {
    override fun getCharCodeCurrency(charcode: String): String {
        TODO("Not yet implemented")
    }

    override fun getNameCurrency(name: String): String {
        TODO("Not yet implemented")
    }

    override fun getValueCurrency(value: Double): Double {
        TODO("Not yet implemented")
    }
}
