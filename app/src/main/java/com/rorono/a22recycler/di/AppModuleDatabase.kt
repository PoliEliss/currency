package com.rorono.a22recycler.di

import android.content.Context
import com.rorono.a22recycler.database.CurrencyDao
import com.rorono.a22recycler.database.CurrencyDataBase
import com.rorono.a22recycler.repository.RepositoryDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModuleDatabase {

    @Provides
    @Singleton
    fun provideRepositoryDataBase(dataBase: CurrencyDataBase): RepositoryDataBase {
        return RepositoryDataBase(database = dataBase)
    }
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