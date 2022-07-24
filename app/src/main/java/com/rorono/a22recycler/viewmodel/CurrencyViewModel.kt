package com.rorono.a22recycler.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rorono.a22recycler.Result
import com.rorono.a22recycler.database.CurrencyDao
import com.rorono.a22recycler.database.CurrencyItem
import com.rorono.a22recycler.database.SaveCurrencyItem
import com.rorono.a22recycler.models.Currency
import com.rorono.a22recycler.repository.Repository
import com.rorono.a22recycler.utils.Rounding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CurrencyViewModel @Inject constructor(private val repository: Repository, private val dataBase: CurrencyDao) :
    ViewModel() {
    val _listCurrency: MutableLiveData<List<Currency>> = MutableLiveData()
    val listCurrency: LiveData<List<Currency>>
        get() = _listCurrency

    private val _messageError: MutableLiveData<String> = MutableLiveData()
    val messageError: LiveData<String>
        get() = _messageError

    val date: MutableLiveData<String> = MutableLiveData(getDate())

    var currencyDatabase: MutableLiveData<List<Currency>> = MutableLiveData()

    var saveCurrencyDatabase: MutableLiveData<List<Currency>> = MutableLiveData()

    fun getDate(): String {
        val currentDate = Date()
        val dataFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dataFormat.format(currentDate)
    }

    fun getCurrency(date: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = repository.getCurrency(date)
                withContext(Dispatchers.Main) {
                    when (response) {
                        is Result.Success -> {
                            _listCurrency.postValue(response.currency.values.toList())
                            setCurrencyDao(response.currency.values.toList(),getDate())
                        }
                        is Result.Error -> {
                            _messageError.postValue(response.error)
                        }
                    }
                }
            }
        }
    }

     fun setCurrencyDao(currency: List<Currency>,date: String) {
        Log.d("TEST","setCurrencyDao ${currency}")
        if ( currency.isNotEmpty() && date==getDate()) {
            var model: CurrencyItem
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    deleteAllData()
                    for (i in currency) {
                        model =
                            CurrencyItem(
                                fullName = i.fullName,
                                charCode = i.charCode,
                                value = i.value
                            )
                        dataBase.insertCurrency(model)
                    }
                }
            }
        }
    }

    private suspend fun deleteAllData() {
        dataBase.deleteAllCurrency()
    }

    fun deleteAllSaveCurrency() {
        Log.d("TEST","Delete ALL CURRENCY")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dataBase.deleteAllSaveCurrency()
            }
        }
    }

    fun deleteSaveCurrency(currency: Currency){
        viewModelScope.launch {
            Log.d("TEST3","${currency}")
            var model: SaveCurrencyItem
            withContext(Dispatchers.IO){
                model = SaveCurrencyItem(
                    fullName = currency.fullName,
                    charCode = currency.charCode,
                    value = currency.value,
                    favorite = 0
                )
                Log.d("TEST3","vieModelDeleteCurrency ${model}")
                dataBase.deleteSaveCurrency(model)
                Log.d("TEST3","Посмотреть данные по удалению валюты ${dataBase.getAllSaveCurrency()}")
            }
        }
        getSaveCurrencyDao()
    }
    fun setSaveCurrencyDao(currency: List<Currency>) {
        Log.d("TEST3","listCurrency11 ${currency}")

        viewModelScope.launch {
            var model: SaveCurrencyItem
            withContext(Dispatchers.IO) {

                for (q in currency) {
                    model =
                        SaveCurrencyItem(
                            fullName = q.fullName,
                            charCode = q.charCode,
                            value = q.value,
                            favorite = 1
                        )
                    dataBase.insertSaveCurrency(model)
                    Log.d("TEST3", "SaveCurrencyModel ${model}")
                }
            }
        }

    }

    fun getSaveCurrencyDao() {
        val currencySaveListDatabase =
            mutableListOf<Currency>()
        viewModelScope.launch {
            var currency: Currency
            val saveCurrencyItem: List<SaveCurrencyItem> =
                withContext(Dispatchers.IO) { dataBase.getAllSaveCurrency() }
            Log.d("TEST15", "getSaveCurrencyDao ${saveCurrencyItem}")
            if (saveCurrencyItem.isEmpty()){
                Log.d("TEST15","ПУСТО!!!")
                saveCurrencyDatabase.value = emptyList()
            }
            for (i in saveCurrencyItem) {
                Log.d("TEST15","ВАЖНЫЙ ТЕСТ ${i}")
                currency =
                    Currency(fullName = i.fullName, charCode = i.charCode, value = i.value, isFavorite = 1)
                currencySaveListDatabase.add(currency)
                Log.d("TEST15","  currencySaveListDatabase ${currencySaveListDatabase}")
                saveCurrencyDatabase.value = currencySaveListDatabase
            }
        }
    }

    fun getCurrencyDao() {
        val currencyListDatabase =
            mutableListOf<Currency>()
        viewModelScope.launch {
            var currency: Currency
            val currencyItem: List<CurrencyItem> =
                withContext(Dispatchers.IO) { dataBase.getAllCurrency() }
            for (i in currencyItem) {
                currency =
                    Currency(fullName = i.fullName, charCode = i.charCode, value = i.value)
                currencyListDatabase.add(currency)
                currencyDatabase.value = currencyListDatabase
            }
        }
    }

    fun converterToCurrency(rate: Double, enteredValue: Double): Double {
        if (rate < 0 || enteredValue < 0) {
            throw IllegalArgumentException()
        }
        val valuate = enteredValue / rate
        return Rounding.getTwoNumbersAfterDecimalPoint(valuate)
    }

    fun transferToRubles(rate: Double, enteredValue: Double): Double {
        if (rate < 0 || enteredValue < 0) {
            throw IllegalArgumentException()
        }
        val valuate = enteredValue * rate
        return Rounding.getTwoNumbersAfterDecimalPoint(valuate)
    }
}


