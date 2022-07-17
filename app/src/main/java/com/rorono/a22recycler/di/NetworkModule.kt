package com.rorono.a22recycler.di

import com.rorono.a22recycler.network.ApiService
import com.rorono.a22recycler.network.RetrofitInstance
import com.rorono.a22recycler.repository.Repository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://www.cbr-xml-daily.ru/archive/"
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService): Repository {
        return Repository(apiService = apiService)
    }

    @Provides
    @Singleton
    fun provideClient():OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(7000, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient):Retrofit {
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit):ApiService {
      return  retrofit.create(ApiService::class.java)
    }
}