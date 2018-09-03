package com.esorokin.currencyconverter.app

import androidx.annotation.StringRes

interface StringProvider {
    fun getString(@StringRes stringRes: Int, vararg formatArgs: Any): String
}
