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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.handleCoroutineException

class ViewModelDataStore(application: Application) : AndroidViewModel(application) {
    val repository = DataStoreRepository(application)

    val test = MutableLiveData<String>()

    val language = MutableLiveData<String>()

    val readThemeFromDataStore = repository.readFromThemeDataStore.asLiveData()

      val readLanguageFromDataStore = repository.readFromLanguageDataStore.asLiveData()

    fun saveToDataStore(key: String, value: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.save(key = key, value = value)
    }


init {
    getCollect()
    getLanguageCollect()
}

    fun getCollect() {
       viewModelScope.launch {
        val result =   repository.readFromThemeDataStore.collect{
            test.postValue(it)
            Log.d("TEST","result ${it}")
        }

       }
    }

    fun getLanguageCollect(){
        viewModelScope.launch {
            val result = repository.readFromLanguageDataStore.collect{
                language.postValue(it)
                Log.d("TEST"," language ${it}")
            }
        }
    }

   /* suspend fun read(key: String):String {
      val result =   repository.read(key)
        Log.d("TEST","Result 56 ${result}")
        return  result ?:"1"
    }*/


    //val readTheneDataStore = repository.readFromThemeDataStore.as
}