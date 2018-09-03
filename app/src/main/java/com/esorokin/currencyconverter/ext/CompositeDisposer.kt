package com.esorokin.currencyconverter.ext

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class CompositeDisposer : Disposer {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}
