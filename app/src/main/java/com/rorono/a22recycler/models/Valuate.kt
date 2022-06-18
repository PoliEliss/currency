package com.rorono.a22recycler.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Valuate(
    @SerializedName("Name")
    val name: String,
    @SerializedName("CharCode")
    val charCode: String,
    @SerializedName("Value")
    val value: Double
):Serializable
