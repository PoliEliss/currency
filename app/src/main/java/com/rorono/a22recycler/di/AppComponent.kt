package com.rorono.a22recycler.di

import android.content.Context
import com.rorono.a22recycler.presentation.CalculateCurrencyFragment
import com.rorono.a22recycler.presentation.CurrencyFragment
import com.rorono.a22recycler.presentation.CurrencyTransferFragment
import com.rorono.a22recycler.presentation.SavedCurrencyFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject(currencyFragment: CurrencyFragment)
    fun inject(currencyTransferFragment: CurrencyTransferFragment)
    fun inject(savedCurrencyFragment: SavedCurrencyFragment)
    fun inject(calculateCurrencyFragment: CalculateCurrencyFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
