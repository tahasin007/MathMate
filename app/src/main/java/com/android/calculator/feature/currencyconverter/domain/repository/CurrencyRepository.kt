package com.android.calculator.feature.currencyconverter.domain.repository

import com.android.calculator.feature.currencyconverter.domain.model.CurrencyRate

interface CurrencyRepository {
    suspend fun fetchAndSaveCurrencyRates(baseCurrency: String)
    fun getCurrencyRates(): CurrencyRate
}