package com.esorokin.currencyconverter.presentation.currency

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.esorokin.currencyconverter.data.Currency
import com.esorokin.currencyconverter.presentation.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface CurrencyView : BaseView {
    fun updateCurrency(currency: Currency)
    fun updateValue(value: String)
    fun updateAvailability(available: Boolean)
}
