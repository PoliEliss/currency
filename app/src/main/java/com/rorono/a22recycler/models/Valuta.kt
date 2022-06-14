package com.rorono.a22recycler.models

import com.google.gson.annotations.SerializedName

data class Valuta(
    @SerializedName("Name")
    val name: String,
    @SerializedName("Value")
    val value: Double
)
