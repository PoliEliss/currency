package com.rorono.a22recycler.repository

import android.util.Log
import com.rorono.a22recycler.Result
import com.rorono.a22recycler.network.ApiService
import com.rorono.a22recycler.network.RetrofitInstance
import com.rorono.a22recycler.settings.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    suspend fun getCurrency(data: String): Result = withContext(Dispatchers.IO)  {
            try {
                val response = apiService.getCurrency(data = data)
                if (response.isSuccessful) {
                    return@withContext Result.Success(response.body()!!.currencies)
                } else {
                    return@withContext Result.Error("Не удалось отобразить данные за этот день \n Пожалуйста, выберите другую дату")
                }
            } catch (e: Exception) {
                return@withContext Result.Error("Отсутствует интернет подключение")
            }
    }
}