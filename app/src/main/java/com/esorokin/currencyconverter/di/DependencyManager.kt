package com.esorokin.currencyconverter.di

import android.content.Context
import com.esorokin.currencyconverter.di.component.AppComponent
import com.esorokin.currencyconverter.di.component.DaggerAppComponent
import com.esorokin.currencyconverter.di.module.AndroidApplicationModule

object DependencyManager {
    lateinit var appComponent: AppComponent

    fun initAppComponent(context: Context) {
        appComponent = DaggerAppComponent.builder()
            .androidApplicationModule(AndroidApplicationModule(context))
            .build()
    }
}
