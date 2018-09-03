package com.esorokin.currencyconverter.model.mapper

import com.esorokin.currencyconverter.data.CurrencyRateTable
import com.esorokin.currencyconverter.dto.RatesResponseDto
import javax.inject.Inject

class RatesMapper @Inject constructor() {

    fun mapRatesResponse(dto: RatesResponseDto) = CurrencyRateTable(
        baseId = dto.baseCurrencyId,
        rates = dto.rates
    )
}
