package com.rorono.a22recycler

import android.app.Application
import android.content.Context
import com.rorono.a22recycler.di.AppComponent
import com.rorono.a22recycler.di.DaggerAppComponent

class MyApplication : Application() {

    init {
        instance = this
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = MyApplication.applicationContext()
    }

    companion object {
        private var instance: MyApplication? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}