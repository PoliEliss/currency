package com.rorono.a22recycler.repository

import android.util.Log
import com.rorono.a22recycler.Result
import com.rorono.a22recycler.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class Repository(private val retrofit: RetrofitInstance) {
    suspend fun getCurrency(data: String): Result = withContext(Dispatchers.IO)  {
            try {
                val response = retrofit.api.getCurrency(data = data)
                if (response.isSuccessful) {
                    Log.d("TEST","response.isSuccessful)")
                    return@withContext Result.Success(response.body()!!.currencies)
                } else {
                    Log.d("TEST","esult.Error1)")
                    return@withContext Result.Error("Не удалось отобразить данные за этот день \n Пожалуйста, выберите другую дату")
                }
            } catch (e: Exception) {
                Log.d("TEST","e exception ${e}")
                return@withContext Result.Error("Отсутствует интернет подключение")
            }
    }
}