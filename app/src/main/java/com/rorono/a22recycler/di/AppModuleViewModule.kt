package com.rorono.a22recycler.di

import androidx.lifecycle.ViewModelProvider
import com.rorono.a22recycler.MainViewModelFactory
import com.rorono.a22recycler.database.CurrencyDao
import com.rorono.a22recycler.database.CurrencyDataBase
import com.rorono.a22recycler.repository.Repository
import com.rorono.a22recycler.repository.RepositoryDataBase
import com.rorono.a22recycler.viewmodel.CurrencyViewModel
import dagger.Module
import dagger.Provides



@Module
class AppModuleViewModule {

    @Provides
    fun viewModelFactory(repositoryDataBase: RepositoryDataBase, repository: Repository): ViewModelProvider.Factory {
        return MainViewModelFactory(repositoryDataBase = repositoryDataBase, repository = repository)
    }

}
