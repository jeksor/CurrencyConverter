package com.esorokin.currencyconverter.di.component

import com.esorokin.currencyconverter.di.module.AndroidApplicationModule
import com.esorokin.currencyconverter.di.module.ApiModule
import com.esorokin.currencyconverter.di.module.BindingModule
import com.esorokin.currencyconverter.presentation.list.CurrenciesPresenter
import com.esorokin.currencyconverter.presentation.currency.CurrencyPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidApplicationModule::class,
        ApiModule::class,
        BindingModule::class
    ]
)
interface AppComponent {
    fun inject(currenciesPresenter: CurrenciesPresenter)
    fun inject(currencyPresenter: CurrencyPresenter)
}
