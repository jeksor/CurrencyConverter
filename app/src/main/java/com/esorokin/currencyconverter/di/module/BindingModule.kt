package com.esorokin.currencyconverter.di.module

import com.esorokin.currencyconverter.model.storage.Database
import com.esorokin.currencyconverter.model.storage.MemoryDatabase
import dagger.Binds
import dagger.Module

@Module
abstract class BindingModule {
    @Binds
    abstract fun database(database: MemoryDatabase): Database
}
