package com.rorono.a22recycler.models

import com.google.gson.annotations.SerializedName

data class Valuate(
    @SerializedName("Name")
    val name: String,
    @SerializedName("CharCode")
    val charCode: String,
    @SerializedName("Value")
    val value: Double
)
