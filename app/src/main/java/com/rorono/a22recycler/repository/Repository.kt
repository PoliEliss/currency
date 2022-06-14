package com.rorono.a22recycler.repository

import com.rorono.a22recycler.network.RetrofitInstance

class Repository(private val retrofit: RetrofitInstance) {

    suspend fun getCurrency() = retrofit.api.getCurrency()
}