package com.rorono.a22recycler

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rorono.a22recycler.database.CurrencyDao
import com.rorono.a22recycler.repository.Repository
import com.rorono.a22recycler.viewmodel.CurrencyViewModel

class MainViewModelFactory(private val repository: Repository, private val dataBase: CurrencyDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyViewModel(repository, dataBase) as T
    }
}