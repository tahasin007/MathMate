package com.android.calculator.feature.currencyconverter.data.repository

import android.util.Log
import com.android.calculator.feature.currencyconverter.data.network.RetrofitClient
import com.android.calculator.feature.currencyconverter.data.source.CurrencyRatePreference
import com.android.calculator.feature.currencyconverter.domain.model.CurrencyRate
import com.android.calculator.feature.currencyconverter.domain.repository.CurrencyRepository
import com.android.calculator.feature.currencyconverter.presentation.utils.CurrencyUtils

class CurrencyRepositoryImpl(
    private val sharedPreferencesHelper: CurrencyRatePreference
) : CurrencyRepository {

    override suspend fun fetchAndSaveCurrencyRates(baseCurrency: String) {
        val response = RetrofitClient.instance.getLatestRates(baseCurrency)
        Log.i(TAG, "fetchAndSaveCurrencyRates: ${response.body()}")

        val rates = response.body()
        if (response.isSuccessful && rates != null) {
            val currencyRates =
                CurrencyRate(rates.time_last_update_utc, rates.base_code, rates.conversion_rates)
            sharedPreferencesHelper.saveRates(currencyRates)
        } else {
            sharedPreferencesHelper.saveRates(CurrencyUtils.defaultCurrencyRate)
        }
    }

    override fun getCurrencyRates(): CurrencyRate {
        return sharedPreferencesHelper.getRates()
    }

    companion object {
        private const val TAG = "CurrencyRepositoryImpl"
    }
}