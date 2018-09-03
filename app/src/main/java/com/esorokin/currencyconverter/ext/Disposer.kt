package com.esorokin.currencyconverter.ext

import io.reactivex.disposables.Disposable

interface Disposer {
    fun addDisposable(disposable: Disposable)

    fun clear()

    fun Disposable.autoDispose(): Disposable = addDisposable(this).let { this }
}
