package com.rorono.a22recycler.repository

import com.rorono.a22recycler.MyApplication
import com.rorono.a22recycler.R
import com.rorono.a22recycler.network.ApiService
import com.rorono.a22recycler.network.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class Repository @Inject constructor(private val apiService: ApiService) {
   
    suspend fun getCurrency(data: String): Result = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getCurrency(data = data)
            if (response.isSuccessful) {
                return@withContext Result.Success(response.body()!!.currencies)
            } else {
                return@withContext Result.Error(MyApplication.applicationContext().getString(R.string.attention_state_error))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(MyApplication.applicationContext().getString(R.string.no_internet_connection))
        }
    }
}