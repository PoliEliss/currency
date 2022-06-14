package com.rorono.a22recycler

import com.rorono.a22recycler.models.Valuate

sealed class Result {

    class Ok(val valuate: Map<String,Valuate>):Result()

    class Error ():Result()

}
