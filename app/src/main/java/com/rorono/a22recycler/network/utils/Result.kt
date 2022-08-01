package com.rorono.a22recycler.network.utils

import com.rorono.a22recycler.models.remotemodels.Currency

sealed class Result {
    data class Success(val currency: Map<String, Currency>) : Result()
    data class Error(val error: String) : Result()
}
