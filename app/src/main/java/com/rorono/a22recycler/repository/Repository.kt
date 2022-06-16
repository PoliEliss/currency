package com.rorono.a22recycler.repository

import com.rorono.a22recycler.Result
import com.rorono.a22recycler.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class Repository(private val retrofit: RetrofitInstance) {
    suspend fun getCurrency(data: String) = withContext(Dispatchers.IO) {
        try {
            val response = retrofit.api.getCurrency(data = data)
            if (response.isSuccessful) {
                val result = Result.Success(response.body()!!.currencies)
                return@withContext result
            } else {
                return@withContext Result.Error("Не удалось отобразить данные за этот день \n Пожалуйста, выберите другую дату")
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e.toString())
        }
    }
}