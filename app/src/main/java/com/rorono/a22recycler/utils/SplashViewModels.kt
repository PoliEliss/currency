package com.rorono.a22recycler.utils

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rorono.a22recycler.settings.DataStoreRepository
import com.rorono.a22recycler.settings.StateDS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModels(application: Application) : AndroidViewModel(application) {

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

        viewModelScope.launch {
            repository.readFromThemeDataStore.collect {
                selectTheme.postValue(it)

            }

        }
    }

    fun getLanguageCollect() {

        viewModelScope.launch {
            val result = repository.readFromLanguageDataStore.collect {
                language.postValue(it)
                Log.d("TEST", " language ${it}")
            }

        }
    }


}