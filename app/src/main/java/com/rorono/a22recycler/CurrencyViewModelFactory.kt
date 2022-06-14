package com.rorono.a22recycler

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rorono.a22recycler.repository.Repository

class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return CurrencyViewModel(repository) as T
    }


}