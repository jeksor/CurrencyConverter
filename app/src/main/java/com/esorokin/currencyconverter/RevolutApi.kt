package com.esorokin.currencyconverter

import com.esorokin.currencyconverter.dto.RatesResponseDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RevolutApi {
    @GET("latest")
    fun rates(@Query("base") baseCurrencyId: String): Single<RatesResponseDto>
}
