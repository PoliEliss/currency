package com.rorono.a22recycler.network

import com.google.gson.annotations.SerializedName
import com.rorono.a22recycler.models.Currency
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{data}.js")
    suspend fun getCurrency(@Path("data") data: String): retrofit2.Response<CurrencyList>

    data class CurrencyList(
        @SerializedName("Valute")
        val currencies: Map<String, Currency>
    )
}