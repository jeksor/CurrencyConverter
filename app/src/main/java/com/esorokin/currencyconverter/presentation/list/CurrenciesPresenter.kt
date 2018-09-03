package com.esorokin.currencyconverter.presentation.list

import com.arellomobile.mvp.InjectViewState
import com.esorokin.currencyconverter.di.DependencyManager
import com.esorokin.currencyconverter.model.interactor.ConverterInteractor
import com.esorokin.currencyconverter.presentation.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class CurrenciesPresenter : BasePresenter<CurrenciesView>() {

    @Inject
    lateinit var interactor: ConverterInteractor

    init {
        DependencyManager.appComponent.inject(this)

        interactor.convertedCurrenciesEmitter
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { viewState.showCurrencies(it) }
            .autoDispose()
    }
}
