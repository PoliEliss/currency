package com.rorono.a22recycler.network.utils

import com.rorono.a22recycler.models.remotemodels.Currency

sealed class CurrencyState {

    data class Success(val data: List<Currency>) : CurrencyState()
    data class Error(val messageError: String) : CurrencyState()
    object Loading : CurrencyState()
}
