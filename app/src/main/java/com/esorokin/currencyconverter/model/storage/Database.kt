package com.esorokin.currencyconverter.model.storage

import com.esorokin.currencyconverter.data.Currency
import com.esorokin.currencyconverter.data.CurrencyValue
import com.esorokin.currencyconverter.data.CurrencyRateTable
import io.reactivex.Completable
import io.reactivex.Maybe

interface Database {
    fun setBaseCurrency(value: CurrencyValue): Completable
    fun getBaseCurrency(): Maybe<CurrencyValue>

    fun getRateTable(baseCurrency: Currency): Maybe<CurrencyRateTable>
    fun addOrUpdateRateTable(rates: CurrencyRateTable): Completable
}
