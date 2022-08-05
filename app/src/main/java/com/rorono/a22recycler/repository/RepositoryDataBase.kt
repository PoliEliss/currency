package com.rorono.a22recycler.repository

import com.rorono.a22recycler.database.CurrencyDataBase
import com.rorono.a22recycler.models.localmodels.CurrencyItem
import com.rorono.a22recycler.models.localmodels.SaveCurrencyItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryDataBase @Inject constructor(val database: CurrencyDataBase) {
    private val currencyDao = database.currencyDao()
    suspend fun insertCurrency(currencyItem: CurrencyItem) {
        withContext(Dispatchers.IO) {
            currencyDao.insertCurrency(currencyItem = currencyItem)
        }
    }

    suspend fun insertSaveCurrency(currencyItem: SaveCurrencyItem) {
        withContext(Dispatchers.IO) {
            currencyDao.insertSaveCurrency(currencyItem = currencyItem)
        }
    }

    suspend fun getAllSaveCurrency(): List<SaveCurrencyItem> {
        val listSaveCurrency = mutableListOf<SaveCurrencyItem>()
        withContext(Dispatchers.IO) {
            listSaveCurrency.addAll(currencyDao.getAllSaveCurrency())
        }
        return listSaveCurrency
    }

    suspend fun getAllCurrency(): List<CurrencyItem> {
        val listSaveCurrency = mutableListOf<CurrencyItem>()
        withContext(Dispatchers.IO) {
            listSaveCurrency.addAll(currencyDao.getAllCurrency())
        }
        return listSaveCurrency
    }

    suspend fun deleteAllCurrency() {
        withContext(Dispatchers.IO) {
            currencyDao.deleteAllCurrency()
        }
    }

    suspend fun deleteSaveCurrency(saveCurrencyItem: SaveCurrencyItem) {
        withContext(Dispatchers.IO) {
            currencyDao.deleteSaveCurrency(saveCurrencyItem = saveCurrencyItem)
        }
    }
}



