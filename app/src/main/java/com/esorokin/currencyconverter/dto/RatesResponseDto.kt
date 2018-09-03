package com.esorokin.currencyconverter.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class RatesResponseDto(
    @SerializedName("base")
    val baseCurrencyId: String,

    @SerializedName("date")
    val date: Date,

    @SerializedName("rates")
    val rates: Map<String, Double>
)
