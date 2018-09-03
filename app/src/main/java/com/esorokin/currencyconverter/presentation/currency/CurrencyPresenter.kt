package com.esorokin.currencyconverter.presentation.currency

import com.arellomobile.mvp.InjectViewState
import com.esorokin.currencyconverter.data.Currency
import com.esorokin.currencyconverter.data.CurrencyValue
import com.esorokin.currencyconverter.di.DependencyManager
import com.esorokin.currencyconverter.model.interactor.ConverterInteractor
import com.esorokin.currencyconverter.model.CurrencyFormatter
import com.esorokin.currencyconverter.presentation.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class CurrencyPresenter(
    initialCurrency: CurrencyValue
) : BasePresenter<CurrencyView>() {

    @Inject
    lateinit var interactor: ConverterInteractor

    @Inject
    lateinit var formatter: CurrencyFormatter

    init {
        DependencyManager.appComponent.inject(this)

        viewState.updateCurrency(initialCurrency.currency)
        viewState.updateValue(formatter.format(initialCurrency.value))
        viewState.updateAvailability(true)

        interactor.convertedCurrenciesEmitter
            .flatMapIterable { it }
            .filter { it.currency == initialCurrency.currency }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(this::updateView)
            .distinctUntilChanged(CurrencyValue::activeBaseCurrency)
            .doOnNext { viewState.updateValue(formatter.format(it.value)) }
            .subscribe(this::updateView)
            .autoDispose()
    }

    private fun updateView(currency: CurrencyValue) {
        if (currency.activeBaseCurrency) {
            viewState.updateAvailability(true)
        } else {
            viewState.updateValue(formatter.format(currency.value))
            viewState.updateAvailability(currency.available)
        }
    }

    fun userSelectNewBaseCurrencyType(type: Currency) {
        interactor.changeBaseCurrencyType(type)
    }

    fun userChangeCurrencyValue(value: String) {
        interactor.changeBaseCurrencyValue(formatter.parse(value))
    }
}
