package com.esorokin.currencyconverter.presentation.list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.esorokin.currencyconverter.data.CurrencyValue
import com.esorokin.currencyconverter.presentation.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface CurrenciesView : BaseView {
    fun showCurrencies(currencies: List<CurrencyValue>)
}
