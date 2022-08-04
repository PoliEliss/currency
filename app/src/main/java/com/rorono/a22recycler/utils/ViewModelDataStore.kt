package com.rorono.a22recycler.settings

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rorono.a22recycler.models.remotemodels.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData
import com.rorono.a22recycler.utils.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.handleCoroutineException

class ViewModelDataStore(application: Application) : BaseViewModel<StateDS>(application = application) {

    val repository = DataStoreRepository(application)
    val selectTheme = MutableLiveData<String>()
    val language = MutableLiveData<String>()

    fun saveToDataStore(key: String, value: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.save(key = key, value = value)
    }


    init {
        getCollect()
        getLanguageCollect()
    }

    fun getCollect() {
        state.value = StateDS.Success
        viewModelScope.launch {
            repository.readFromThemeDataStore.collect {
                selectTheme.postValue(it)

            }
            state.value=StateDS.Success
        }
    }

    fun getLanguageCollect() {
        state.value = StateDS.Success
        viewModelScope.launch {
         repository.readFromLanguageDataStore.collect {
                language.postValue(it)
                Log.d("TEST", " language ${it}")
            }
            state.value=StateDS.Success
        }
    }

    /* suspend fun read(key: String):String {
       val result =   repository.read(key)
         Log.d("TEST","Result 56 ${result}")
         return  result ?:"1"
     }*/

}

sealed class StateDS {
    object Loading : StateDS()
    object Success : StateDS()
}