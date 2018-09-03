package com.esorokin.currencyconverter.presentation

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.esorokin.currencyconverter.ext.CompositeDisposer
import com.esorokin.currencyconverter.ext.Disposer

abstract class BasePresenter<V : MvpView>(disposer: Disposer = CompositeDisposer()) : MvpPresenter<V>(), Disposer by disposer {
    override fun onDestroy() {
        super.onDestroy()
        clear()
    }
}
