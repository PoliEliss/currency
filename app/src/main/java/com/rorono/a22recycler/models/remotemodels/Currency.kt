package com.rorono.a22recycler.models.remotemodels

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Currency(
    @SerializedName("Name")
    var fullName: String,
    @SerializedName("CharCode")
    val charCode: String,
    @SerializedName("Value")
    var value: Double,
    var isFavorite: Int = 0
) : Serializable
