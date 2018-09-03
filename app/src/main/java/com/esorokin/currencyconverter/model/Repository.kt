package com.esorokin.currencyconverter.model

import com.esorokin.currencyconverter.RevolutApi
import com.esorokin.currencyconverter.data.Currency
import com.esorokin.currencyconverter.data.CurrencyRateTable
import com.esorokin.currencyconverter.data.CurrencyValue
import com.esorokin.currencyconverter.model.mapper.RatesMapper
import com.esorokin.currencyconverter.model.storage.Database
import com.esorokin.currencyconverter.utils.Hardcode
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class Repository @Inject constructor(
    private val database: Database,
    private val api: RevolutApi,
    private val mapper: RatesMapper
) {

    fun getCurrencies(): Observable<List<Currency>> = Observable.just(Hardcode.availableCurrencies)

    private val rateTableUpdatesRelay = BehaviorRelay.create<CurrencyRateTable>()
    fun getRateTable(base: Currency): Observable<CurrencyRateTable> = rateTableUpdatesRelay
        .filter { it.baseId == base.id }
        .also {
            database.getRateTable(base)
                .defaultIfEmpty(getEmptyRates(base))
                .onErrorReturnItem(getEmptyRates(base))
                .subscribeBy {
                    rateTableUpdatesRelay.accept(it)
                }
        }

    fun addOrUpdateRateTable(rateTable: CurrencyRateTable): Completable = database.addOrUpdateRateTable(rateTable)
        .doOnComplete { rateTableUpdatesRelay.accept(rateTable) }

    private val baseCurrencyUpdatesRelay = BehaviorRelay.create<CurrencyValue>()
    fun getBaseCurrency(): Observable<CurrencyValue> {
        return baseCurrencyUpdatesRelay
            .also {
                database.getBaseCurrency()
                    .toSingle()
                    .onErrorResumeNext { getDefaultBaseCurrency() }
                    .subscribeBy {
                        baseCurrencyUpdatesRelay.accept(it)
                    }
            }
    }

    fun setBaseCurrency(value: CurrencyValue): Completable = database.setBaseCurrency(value)
        .doOnComplete { baseCurrencyUpdatesRelay.accept(value) }

    fun updateRates(baseCurrency: Currency): Completable {
        return api.rates(baseCurrency.id)
            .map(mapper::mapRatesResponse)
            .flatMapCompletable { addOrUpdateRateTable(it) }
    }

    private fun getDefaultBaseCurrency(): Single<CurrencyValue> = getCurrencies()
        .firstOrError()
        .map { CurrencyValue(it.first(), 1.0, activeBaseCurrency = true) }

    private fun getEmptyRates(base: Currency) = CurrencyRateTable(baseId = base.id, rates = emptyMap())
}
