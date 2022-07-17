package com.rorono.a22recycler.di

import android.content.Context
import com.rorono.a22recycler.database.CurrencyDao
import com.rorono.a22recycler.database.CurrencyDataBase
import com.rorono.a22recycler.presentation.CurrencyFragment
import com.rorono.a22recycler.presentation.CurrencyTransferFragment
import com.rorono.a22recycler.presentation.MainActivity
import com.rorono.a22recycler.presentation.SavedCurrencyFragment
import com.rorono.a22recycler.viewmodel.CurrencyViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(currencyFragment: CurrencyFragment)
    fun inject(currencyTransferFragment: CurrencyTransferFragment)
    fun inject(savedCurrencyFragment: SavedCurrencyFragment)

    @Component.Factory
    interface factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
