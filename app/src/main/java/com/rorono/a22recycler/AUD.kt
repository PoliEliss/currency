package com.rorono.a22recycler

data class AUD(
    override val CharCode: String,
  //  val ID: String,
    override val Name: String,
  //  val Nominal: Int,
  //  val NumCode: String,
  //  val Previous: Double,
    override val Value: Double
) :GetValueCurrency