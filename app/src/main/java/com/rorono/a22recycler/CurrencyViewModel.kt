package com.rorono.a22recycler


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rorono.a22recycler.models.Currency
import com.rorono.a22recycler.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class CurrencyViewModel(private val repository: Repository) : ViewModel() {
    val listCurrency: MutableLiveData<List<Currency>> = MutableLiveData()

    val messageError: MutableLiveData<String> = MutableLiveData()

    val date:MutableLiveData<String> = MutableLiveData(getData())

    fun getData(): String {
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
                            listCurrency.postValue(response.currency.values.toList())
                        }
                        is Result.Error -> {
                            messageError.postValue(response.error)
                        }
                    }
                }
            }
        }
    }
}


