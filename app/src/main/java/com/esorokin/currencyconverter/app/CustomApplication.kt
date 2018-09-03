package com.esorokin.currencyconverter.app

import android.app.Application
import com.esorokin.currencyconverter.di.DependencyManager
import com.esorokin.currencyconverter.utils.BuildUtils
import timber.log.Timber

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupLogSystem()
        setupDi()
    }

    private fun setupLogSystem() {
        if (BuildUtils.isTurnLogs) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupDi() {
        DependencyManager.initAppComponent(this)
    }
}
