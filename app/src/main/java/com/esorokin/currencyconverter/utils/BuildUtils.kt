package com.esorokin.currencyconverter.utils

import com.esorokin.currencyconverter.BuildConfig

object BuildUtils {
    val isRelease: Boolean
        get() = !BuildConfig.DEBUG

    val isDebug: Boolean
        get() = BuildConfig.DEBUG

    val isTurnLogs: Boolean
        get() = BuildConfig.IS_TURN_LOGS
}
