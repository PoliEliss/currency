package com.rorono.a22recycler.network

import com.google.gson.annotations.SerializedName
import com.rorono.a22recycler.models.Valuta
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("2022-04-05.js")
    suspend fun getCurrency(): CurrencyList

    data class CurrencyList(
        @SerializedName("Valute")
        val currencies: Map<String, Valuta>
    )
}