package com.esorokin.currencyconverter.model

import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyFormatter @Inject constructor(locale: Locale) {

    private val numberFormat = NumberFormat.getNumberInstance(locale).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    fun format(value: Double): String = numberFormat.format(value)

    fun parse(value: String): Double = when (value) {
        "", ",", "." -> 0.0
        else -> numberFormat.parse(value).toDouble()
    }
}
