package com.rorono.a22recycler

import com.rorono.a22recycler.models.Valuta

sealed class Result {

    class Ok(val valuta: Map<String,Valuta>):Result()

    class Error ():Result()

}
