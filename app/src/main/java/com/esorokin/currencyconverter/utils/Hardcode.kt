package com.esorokin.currencyconverter.utils

import com.esorokin.currencyconverter.data.Currency

object Hardcode {
    val availableCurrencies = listOf(
        createCurrency("USD", "United States Dollar"),
        createCurrency("THB", "Thai Baht"),
        createCurrency("GBP", "Great British Pound"),
        createCurrency("AUD", "Australian Dollar")
    )

    private fun createCurrency(id: String, fullName: String) =
        Currency(id, id, fullName, "https://flagpedia.net/data/flags/h40/${id.toLowerCase().subSequence(0, 2)}.png")
}
