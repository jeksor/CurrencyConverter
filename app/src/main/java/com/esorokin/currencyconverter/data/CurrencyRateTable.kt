package com.esorokin.currencyconverter.data

data class CurrencyRateTable(
    val baseId: String,
    val rates: Map<String, Double>
)
