package com.rorono.a22recycler.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rorono.a22recycler.models.localmodels.CurrencyItem
import com.rorono.a22recycler.models.localmodels.SaveCurrencyItem
import com.rorono.a22recycler.models.remotemodels.Currency
import com.rorono.a22recycler.network.utils.Result
import com.rorono.a22recycler.repository.Repository
import com.rorono.a22recycler.repository.RepositoryDataBase
import com.rorono.a22recycler.utils.Rounding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CurrencyViewModel @Inject constructor(
    private val repository: Repository,
    private val repositoryDataBase: RepositoryDataBase
) : BaseViewModel<CurrencyState>() {

    val date: MutableLiveData<String> = MutableLiveData(getDate())

    private val _currencyDataBase = MutableLiveData<List<Currency>>()
    val currencyDatabase: LiveData<List<Currency>>
        get() = _currencyDataBase

    var saveCurrencyDatabase: MutableLiveData<List<Currency>> = MutableLiveData()


    fun getDate(): String {
        val currentDate = Date()
        val dataFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dataFormat.format(currentDate)
    }

    fun getCurrency(date: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                state.postValue(CurrencyState.Loading)
                val response = repository.getCurrency(date)
                withContext(Dispatchers.Main) {
                    when (response) {
                        is Result.Success -> {
                            val list = mutableListOf<Currency>()
                            list.addAll(response.currency.values.toList())
                            list.add(Currency("Российский рубль", "RUB", 1.0, 0))
                            state.postValue(CurrencyState.Success(response.currency.values.toList()))
                            setCurrencyDao(list, getDate())
                        }
                        is Result.Error -> {
                            state.postValue(CurrencyState.Error("Ошибка"))
                        }
                    }
                }
            }
        }
    }

    private fun setCurrencyDao(currency: List<Currency>, date: String) {
        if (currency.isNotEmpty() && date == getDate()) {
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
                        repositoryDataBase.insertCurrency(model)
                    }
                }
            }
        }
    }

    private suspend fun deleteAllData() {
        repositoryDataBase.deleteAllCurrency()
    }

    fun deleteSaveCurrency(currency: Currency) {
        viewModelScope.launch {
            var model: SaveCurrencyItem
            withContext(Dispatchers.IO) {
                model = SaveCurrencyItem(
                    fullName = currency.fullName,
                    charCode = currency.charCode,
                    value = currency.value,
                    favorite = 0
                )
                repositoryDataBase.deleteSaveCurrency(model)
            }
            getSaveCurrencyDao()
        }
    }

    private fun mapSaveCurrencyItem(currency: List<Currency>): List<SaveCurrencyItem> {
        val modelList = mutableListOf<SaveCurrencyItem>()
        for (i in currency) {
            val model = SaveCurrencyItem(
                fullName = i.fullName,
                charCode = i.charCode,
                value = i.value,
                favorite = 0
            )
            modelList.add(model)
        }
        return modelList
    }

    private fun mapCurrencyItem(currency: List<SaveCurrencyItem>): List<Currency> {
        val listCurrency = mutableListOf<Currency>()
        for (i in currency) {
            val currencyItem = Currency(
                fullName = i.fullName,
                charCode = i.charCode,
                value = i.value,
                isFavorite = 0
            )
            listCurrency.add(currencyItem)
        }
        return listCurrency
    }

    fun setSaveCurrencyDao(currency: List<Currency>) {
        val listSaveCurrencyItem = mapSaveCurrencyItem(currency)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                for (q in listSaveCurrencyItem) {
                    repositoryDataBase.insertSaveCurrency(q)
                }
            }
            getSaveCurrencyDao()
        }
    }

    fun getSaveCurrencyDao() {
        val currencySaveListDatabase =
            mutableListOf<Currency>()
        viewModelScope.launch {
            val saveCurrencyItem: List<SaveCurrencyItem> =
                repositoryDataBase.getAllSaveCurrency()
            if (saveCurrencyItem.isEmpty()) {
                saveCurrencyDatabase.value = emptyList()
            } else {
                currencySaveListDatabase.addAll(mapCurrencyItem(saveCurrencyItem))
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
                repositoryDataBase.getAllCurrency()
            for (i in currencyItem) {
                currency =
                    Currency(fullName = i.fullName, charCode = i.charCode, value = i.value)
                currencyListDatabase.add(currency)
                _currencyDataBase.value = currencyListDatabase
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

    fun transferToCurrency(rate: Double, enteredValue: Double, convertedTo: Double): Double {
        if (rate < 0 || enteredValue < 0) {
            throw IllegalArgumentException()
        }
        val result = (rate * enteredValue) / convertedTo
        return Rounding.getTwoNumbersAfterDecimalPoint(result)
    }
}


