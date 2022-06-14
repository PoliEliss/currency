package com.rorono.a22recycler

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rorono.a22recycler.models.Valuta
import com.rorono.a22recycler.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CurrencyViewModel(private val repository: Repository) : ViewModel() {

    val listCurrency: MutableLiveData<List<Valuta>> = MutableLiveData()

    var changeCurrency: Valuta? = null

    fun getData(): String {
        val currentDate = Date()
        val dataFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dataFormat.format(currentDate)
    }

    fun getCurrency() {
        viewModelScope.launch {
            val response = repository.getCurrency()
            val list = response.currencies.values.toList()
            listCurrency.value = list
        }
    }
}


