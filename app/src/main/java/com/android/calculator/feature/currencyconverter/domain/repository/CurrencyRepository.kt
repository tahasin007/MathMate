package com.android.calculator.feature.currencyconverter.domain.repository

import com.android.calculator.feature.currencyconverter.domain.model.CurrencyRate
import com.android.calculator.feature.currencyconverter.domain.model.CurrencyState

interface CurrencyRepository {
    suspend fun fetchAndSaveCurrencyRates(baseCurrency: String)
    suspend fun getCurrencyRates(): CurrencyRate
    suspend fun saveCurrencyState(currencyState: CurrencyState)
    suspend fun getCurrencyState(): CurrencyState
}