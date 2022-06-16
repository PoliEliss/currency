package com.rorono.a22recycler

import com.rorono.a22recycler.models.Valuate
import java.lang.Exception

sealed class Result {
    data class Success(val valuate: Map<String, Valuate>) : Result()
    data class Error(val error: String) : Result()
}
