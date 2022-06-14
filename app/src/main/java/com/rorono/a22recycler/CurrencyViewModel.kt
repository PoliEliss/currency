package com.rorono.a22recycler

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rorono.a22recycler.models.Valuate
import com.rorono.a22recycler.repository.Repository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CurrencyViewModel(private val repository: Repository) : ViewModel() {
    val listCurrency: MutableLiveData<List<Valuate>> = MutableLiveData()
    var changeCurrency: Valuate? = null


    fun getData(): String {
        Log.d("TEST","")
        val currentDate = Date()
        Log.d("TEST","currentData ${currentDate}")
        val dataFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dataFormat.format(currentDate)

    }


    fun getCurrency(data:String) {
        viewModelScope.launch {
            val response = repository.getCurrency(data)
            val list = response.currencies.values.toList()
            listCurrency.value = list
        }
    }
}


