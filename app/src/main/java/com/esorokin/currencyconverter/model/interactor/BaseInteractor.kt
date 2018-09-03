package com.esorokin.currencyconverter.model.interactor

import com.esorokin.currencyconverter.ext.CompositeDisposer
import com.esorokin.currencyconverter.ext.Disposer
import io.reactivex.disposables.Disposable

open class BaseInteractor protected constructor(disposer: Disposer = CompositeDisposer()) : Disposer by disposer, Disposable {
    private var disposed = false

    override fun isDisposed(): Boolean = disposed

    final override fun dispose() {
        disposed = true
    }
}
