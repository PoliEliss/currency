package com.rorono.a22recycler.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency")
    suspend fun getAllCurrency(): List<CurrencyItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currencyItem:List<CurrencyItem>)

    @Query("DELETE FROM currency")
    suspend fun deleteAllCurrency()
}