package com.rorono.a22recycler.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Currency(
    @SerializedName("Name")
    var fullName: String,
    @SerializedName("CharCode")
    val charCode: String,
    @SerializedName("Value")
    val value: Double,
    var isFavorite: Int = 0
) : Serializable
