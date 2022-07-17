package com.rorono.a22recycler.di

import android.app.Application
import android.content.Context
import com.rorono.a22recycler.database.CurrencyDao
import com.rorono.a22recycler.database.CurrencyDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModuleDatabase {


    @Provides
    @Singleton
    fun provideCurrencyDao(dataBase: CurrencyDataBase): CurrencyDao {
        return dataBase.currencyDao()
    }

    @Provides
    @Singleton
    fun provideDataBase(context: Context): CurrencyDataBase {
        return CurrencyDataBase.getInstance(context)
    }

}