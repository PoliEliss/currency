package com.rorono.a22recycler

import android.app.Application
import com.rorono.a22recycler.di.AppComponent
import com.rorono.a22recycler.di.DaggerAppComponent

class MyApplication:Application() {

    val appComponent:AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

}