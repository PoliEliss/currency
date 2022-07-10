package com.rorono.a22recycler.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rorono.a22recycler.Result
import com.rorono.a22recycler.database.CurrencyDao

import com.rorono.a22recycler.database.MyCurrencyDaoModel

import com.rorono.a22recycler.models.Currency
import com.rorono.a22recycler.repository.Repository
import com.rorono.a22recycler.utils.Rounding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CurrencyViewModel(private val repository: Repository, private val dataBase: CurrencyDao) :
    ViewModel() {
    private val _listCurrency: MutableLiveData<List<Currency>> = MutableLiveData()
    val listCurrency: LiveData<List<Currency>>
        get() = _listCurrency

    private val _messageError: MutableLiveData<String> = MutableLiveData()
    val messageError: LiveData<String>
        get() = _messageError

    val date: MutableLiveData<String> = MutableLiveData(getDate())
    var currencyX: List<Currency> = listOf()
    var _currencyDatabase: MutableLiveData<List<Currency>> = MutableLiveData()
    val currencyDatabase: LiveData<List<Currency>>
        get() = _currencyDatabase
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
                            setCurrencyDao(response.currency.values.toList())
                        }
                        is Result.Error -> {
                            _messageError.postValue(response.error)
                        }
                    }
                }
            }
        }
    }

    /*fun changeCurrencyBD(item: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyDatabase.value!!.forEach { currency ->
                if (item.fullName == currency.fullName) {
                    currency.isFavorite = 1
                }
            }
            setCurrencyDao()
        }
    }*/

    fun setCurrencyDao(currency: List<Currency>) {//исправила оно работает
        if (currency.isNotEmpty()) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    Log.d("TEST111","set ${currency.filter { it1->it1.isFavorite==1 }}")
                    val currencyString: String
                    deleteAllData()
                    withContext(Dispatchers.Default) {
                        val gson = Gson()
                        currencyString = gson.toJson(currency).toString()
                    }
                    val myCurrencyDaoModel = MyCurrencyDaoModel(currency = currencyString)
                    dataBase.insertCurrency(myCurrencyDaoModel)
                    _currencyDatabase.postValue(currency)
                }
            }
        }
    }

    suspend fun deleteAllData() {//исправила
        dataBase.deleteAllCurrency()
    }

    fun getCurrencyDao() {
        viewModelScope.launch {
            val gson = Gson()
            try {
                val myCurrencyDaoModel = withContext(Dispatchers.IO) { dataBase.getAllCurrency() }
             //   Log.d("TEST111", "myCurremcyDaoModel ${myCurrencyDaoModel}")

                val itemType = object : TypeToken<List<Currency>>() {}.type
                val currencyList =
                    gson.fromJson<List<Currency>>(myCurrencyDaoModel.currency, itemType)

                Log.d("TEST111", "currency.list ${currencyList.filter { it1->it1.isFavorite==1 }}")

                _currencyDatabase.postValue(currencyList)

            } catch (e: Exception) {
            }
        }
    }
    fun updateCurrencyDatabase(list:List<Currency>){
        _currencyDatabase.value = list
    }
    /*

     fun deleteSaveCurrency(currency: Currency) {
         viewModelScope.launch {
             Log.d("TEST3", "${currency}")
             var model: SaveCurrencyItem
             withContext(Dispatchers.IO) {
                 model = SaveCurrencyItem(
                     fullName = currency.fullName,
                     charCode = currency.charCode,
                     value = currency.value,
                     favorite = 0
                 )
                 Log.d("TEST3", "vieModelDeleteCurrency ${model}")
                 dataBase.deleteSaveCurrency(model)
                 getSaveCurrencyDao()
                 Log.d(
                     "TEST3",
                     "Посмотреть данные по удалению валюты ${dataBase.getAllSaveCurrency()}"
                 )
             }
         }

     }*/

    /*  fun setSaveCurrencyDao(currency: List<Currency>) {
          Log.d("TEST8", "listCurrency ${currency}")
          viewModelScope.launch {
              var model: SaveCurrencyItem
              withContext(Dispatchers.IO) {
                  for (i in currency) {
                      model =
                          SaveCurrencyItem(
                              fullName = i.fullName,
                              charCode = i.charCode,
                              value = i.value,
                              favorite = 1
                          )
                      dataBase.insertSaveCurrency(model)
                      Log.d("TEST8", "SaveCurrencyModel ${model}")
                  }
              }
          }
          getSaveCurrencyDao()
      }*/


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


