package com.esorokin.currencyconverter.model.interactor

import com.esorokin.currencyconverter.data.Currency
import com.esorokin.currencyconverter.data.CurrencyRateTable
import com.esorokin.currencyconverter.data.CurrencyValue
import com.esorokin.currencyconverter.model.Repository
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function3
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConverterInteractor @Inject constructor(
    private val repository: Repository
) : BaseInteractor() {

    private var convertDisposable: Disposable? = null
    private var changeBaseCurrencyTypeDisposable: Disposable? = null
    private var changeBaseCurrencyValueDisposable: Disposable? = null
    private var updateRatesDisposable: Disposable? = null

    private val convertedCurrenciesRelay = BehaviorRelay.create<List<CurrencyValue>>()
    val convertedCurrenciesEmitter: Observable<List<CurrencyValue>> = convertedCurrenciesRelay

    init {
        Observable.interval(0,1, TimeUnit.SECONDS)
            .subscribe { updateRates() }
            .autoDispose()

        repository.getBaseCurrency()
            .firstElement()
            .subscribe { subscribeToConverts(it.currency) }
            .autoDispose()
    }

    fun changeBaseCurrencyType(newType: Currency) {
        changeBaseCurrencyTypeDisposable?.dispose()
        changeBaseCurrencyTypeDisposable = repository.getBaseCurrency()
            .firstOrError()
            .flatMapCompletable {
                val newBase = it.copy(currency = newType, available = true)
                repository.setBaseCurrency(newBase)
                    .andThen(repository.updateRates(newBase.currency))
                    .onErrorComplete()
            }
            .subscribe()

        subscribeToConverts(newType)
    }

    fun changeBaseCurrencyValue(newValue: Double) {
        changeBaseCurrencyValueDisposable?.dispose()
        changeBaseCurrencyValueDisposable = repository.getBaseCurrency()
            .firstOrError()
            .flatMapCompletable { repository.setBaseCurrency(it.copy(value = newValue)) }
            .subscribe()
    }

    fun updateRates() {
        val updateNotInProgress = updateRatesDisposable.let { it == null || it.isDisposed }
        if (updateNotInProgress) {
            updateRatesDisposable = repository.getBaseCurrency()
                .firstOrError()
                .flatMapCompletable { repository.updateRates(it.currency) }
                .onErrorComplete()
                .subscribe()
        }
    }

    override fun clear() {
        changeBaseCurrencyTypeDisposable?.dispose()
        changeBaseCurrencyValueDisposable?.dispose()
        updateRatesDisposable?.dispose()
    }

    private fun subscribeToConverts(baseCurrency: Currency) {
        convertDisposable?.dispose()
        convertDisposable = Observable.combineLatest<List<Currency>, CurrencyValue, CurrencyRateTable, List<CurrencyValue>>(
            repository.getCurrencies(),
            repository.getBaseCurrency().distinctUntilChanged(CurrencyValue::value),
            repository.getRateTable(baseCurrency),
            Function3 { currencies, base, rateTable ->
                currencies.map { currency ->
                    rateTable.rates[currency.id]?.let { rate ->
                        CurrencyValue(currency, rate * base.value)
                    } ?: CurrencyValue(currency, .0, available = false)
                }.let {
                    listOf(base, *it.filter { it.currency != base.currency }.toTypedArray())
                }
            }
        ).subscribe {
            convertedCurrenciesRelay.accept(it)
        }
    }
}
