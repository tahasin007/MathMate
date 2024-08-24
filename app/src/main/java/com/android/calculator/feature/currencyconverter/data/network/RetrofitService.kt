package com.android.calculator.feature.currencyconverter.data.network

import com.android.calculator.feature.currencyconverter.domain.model.CurrencyRate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    @GET("latest/{baseCurrency}")
    suspend fun getLatestRates(
        @Path("baseCurrency")
        baseCurrency: String
    ): Response<CurrencyRate>
}