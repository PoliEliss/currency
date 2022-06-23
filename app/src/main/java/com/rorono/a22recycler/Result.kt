package com.rorono.a22recycler

import com.rorono.a22recycler.models.Currency

sealed class Result {
    data class Success(val currency: Map<String, Currency>) : Result()
    data class Error(val error: String) : Result()
}
