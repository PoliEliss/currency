package com.rorono.a22recycler.database


import androidx.room.*
import com.rorono.a22recycler.models.localmodels.CurrencyItem
import com.rorono.a22recycler.models.localmodels.SaveCurrencyItem


@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency")
    suspend fun getAllCurrency(): List<CurrencyItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currencyItem: CurrencyItem)

    @Query("DELETE FROM currency")
    suspend fun deleteAllCurrency()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaveCurrency(currencyItem: SaveCurrencyItem)

    @Query("SELECT * FROM save_currency")
    suspend fun getAllSaveCurrency(): List<SaveCurrencyItem>

    @Query("DELETE FROM save_currency")
    suspend fun deleteAllSaveCurrency()

    @Delete
    fun deleteSaveCurrency(saveCurrencyItem: SaveCurrencyItem)
}