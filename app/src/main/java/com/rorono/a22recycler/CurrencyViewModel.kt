package com.rorono.a22recycler

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rorono.a22recycler.models.Valuate
import com.rorono.a22recycler.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class CurrencyViewModel(private val repository: Repository) : ViewModel() {
    val listCurrency: MutableLiveData<List<Valuate>> = MutableLiveData()
    val messageError: MutableLiveData<String> = MutableLiveData()
    var changeCurrency: Valuate? = null

    fun getData(): String {
        val currentDate = Date()
        val dataFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dataFormat.format(currentDate)
    }

    fun getCurrency(data: String) {
       viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = repository.getCurrency(data)
                withContext(Dispatchers.Main) {
                    when (response) {
                        is Result.Success -> {
                            Log.d("TEST","SUCCESS")
                            listCurrency.postValue(response.valuate.values.toList())
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


