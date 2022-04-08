package com.rorono.a22recycler

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rorono.a22recycler.models.CbrEntity
import com.rorono.a22recycler.network.NetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.text.SimpleDateFormat
import java.util.*

class CurrencyViewModel() : ViewModel() {

    private val service: NetworkService = NetworkService()

    var changeCurrency: Money? = null
    var nam:String ="2022-04-07"

         fun getData(): String {
        val currentDate = Date()
        val dataFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dataFormat.format(currentDate)
    }

    fun getCbrEntity(block: (cbr: CbrEntity) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)

            block(service.getCbrEntity(nam))
        }
    }

    // fun getValute(){
    //  viewModelScope.launch {
    //   val responce:Valute = repository.getValute()
    //   myResponce.value = responce

    //}
}


//}