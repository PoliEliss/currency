package com.rorono.a22recycler.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://www.cbr-xml-daily.ru/archive/"

object RetrofitInstance {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(7000,TimeUnit.SECONDS)
        .readTimeout(100,TimeUnit.SECONDS).build()
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}