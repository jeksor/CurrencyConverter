package com.esorokin.currencyconverter.model.storage

import com.esorokin.currencyconverter.data.Currency
import com.esorokin.currencyconverter.data.CurrencyRateTable
import com.esorokin.currencyconverter.data.CurrencyValue
import io.reactivex.Completable
import io.reactivex.Maybe
import javax.inject.Inject

class MemoryDatabase @Inject constructor() : Database {
    private var ratesMap: MutableMap<String, CurrencyRateTable> = hashMapOf()
    private var baseCurrency: CurrencyValue? = null

    override fun getRateTable(baseCurrency: Currency) = ratesMap[baseCurrency.id]?.let { Maybe.just(it) } ?: Maybe.empty()

    override fun addOrUpdateRateTable(rates: CurrencyRateTable): Completable {
        ratesMap[rates.baseId] = rates
        return Completable.complete()
    }

    override fun setBaseCurrency(value: CurrencyValue): Completable {
        baseCurrency = value
        return Completable.complete()
    }

    override fun getBaseCurrency() = baseCurrency.takeIf { it != null }?.let { Maybe.just(it) } ?: Maybe.empty()
}
