package com.rorono.a22recycler

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rorono.a22recycler.database.CurrencyDao
import com.rorono.a22recycler.repository.Repository
import com.rorono.a22recycler.repository.RepositoryDataBase
import com.rorono.a22recycler.viewmodel.CurrencyViewModel
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val repository: Repository, private val repositoryDataBase: RepositoryDataBase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyViewModel(repository, repositoryDataBase) as T
    }
}