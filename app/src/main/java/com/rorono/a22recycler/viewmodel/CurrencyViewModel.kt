package com.rorono.a22recycler.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rorono.a22recycler.Result
import com.rorono.a22recycler.database.CurrencyDao
import com.rorono.a22recycler.database.CurrencyItem
import com.rorono.a22recycler.models.Currency
import com.rorono.a22recycler.repository.Repository
import com.rorono.a22recycler.utils.Rounding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class CurrencyViewModel(private val repository: Repository, private val dataBase: CurrencyDao) :
    ViewModel() {
    private val _listCurrency: MutableLiveData<List<Currency>> = MutableLiveData()
    val listCurrency: LiveData<List<Currency>>
        get() = _listCurrency

    private val _messageError: MutableLiveData<String> = MutableLiveData()
    val messageError: LiveData<String>
        get() = _messageError

    val date: MutableLiveData<String> = MutableLiveData(getDate())

    var currencyDatabase: MutableLiveData<List<Currency>> = MutableLiveData()

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
                            setCurrencyDao(response.currency.values.toList(), date)
                        }
                        is Result.Error -> {
                            _messageError.postValue(response.error)
                        }
                    }
                }
            }
        }
    }

    private fun setCurrencyDao(currency: List<Currency>, date: String) {
        if (date == getDate() && currency.isNotEmpty()) {
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

