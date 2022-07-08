package com.rorono.a22recycler.database


import androidx.room.*


@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency")
    suspend fun getAllCurrency(): MyCurrencyDaoModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currencyItem: MyCurrencyDaoModel)

    @Query("DELETE FROM currency")
    suspend fun deleteAllCurrency()

}