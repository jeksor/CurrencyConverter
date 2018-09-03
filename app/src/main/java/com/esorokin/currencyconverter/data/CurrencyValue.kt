package com.esorokin.currencyconverter.data

import com.esorokin.currencyconverter.ui.adapter.DisplayableItem

data class CurrencyValue(
    val currency: Currency,
    val value: Double,
    val activeBaseCurrency: Boolean = false,
    val available: Boolean = true
) : DisplayableItem
