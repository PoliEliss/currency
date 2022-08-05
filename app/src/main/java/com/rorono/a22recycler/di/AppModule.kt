package com.rorono.a22recycler.di

import dagger.Module

@Module(includes = [AppModuleDatabase::class, NetworkModule::class, AppModuleViewModule::class])
class AppModule