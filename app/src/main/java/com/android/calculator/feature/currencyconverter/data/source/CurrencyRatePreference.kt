package com.android.calculator.feature.currencyconverter.data.source

import android.content.Context
import android.content.SharedPreferences
import com.android.calculator.feature.currencyconverter.domain.model.CurrencyRate
import com.android.calculator.feature.currencyconverter.domain.model.CurrencyState
import com.android.calculator.feature.currencyconverter.presentation.utils.CurrencyUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrencyRatePreference(context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("currency_rate_pref", Context.MODE_PRIVATE)
    }

    fun saveRates(currencyRates: CurrencyRate) {
        val editor = sharedPreferences.edit()
        val ratesJson = Gson().toJson(currencyRates.conversion_rates)
        editor.putString("currency_rates", ratesJson)
        editor.putString("currency_base", currencyRates.base_code)
        editor.putString("currency_date", currencyRates.time_last_update_utc)
        editor.apply()
    }

    fun getRates(): CurrencyRate {
        val ratesJson = sharedPreferences.getString("currency_rates", null)
            ?: Gson().toJson(CurrencyUtils.defaultCurrencyRate.conversion_rates)
        val baseCode = sharedPreferences.getString("currency_base", "")
            ?: CurrencyUtils.defaultCurrencyRate.base_code
        val timeLastUpdateUtc = sharedPreferences.getString("currency_date", "")
            ?: CurrencyUtils.defaultCurrencyRate.time_last_update_utc
        val conversionRates: Map<String, Double> =
            Gson().fromJson(ratesJson, object : TypeToken<Map<String, Double>>() {}.type)
        return CurrencyRate(timeLastUpdateUtc, baseCode, conversionRates)
    }

    fun saveCurrencyState(state: CurrencyState) {
        with(sharedPreferences.edit()) {
            putString("fromCurrency", state.fromCurrency)
            putString("toCurrency", state.toCurrency)
            putString("fromValue", state.fromValue)
            putString("toValue", state.toValue)
            putString("fromToExchangeRate", state.fromToExchangeRate)
            putString("toFromExchangeRate", state.toFromExchangeRate)
            putString("lastUpdatedInLocalTime", state.lastUpdatedInLocalTime)
            putString("currentView", state.currentView.name)
            apply()
        }
    }

    fun getCurrencyState(): CurrencyState {
        return CurrencyState(
            fromCurrency = sharedPreferences.getString("fromCurrency", "USD") ?: "USD",
            toCurrency = sharedPreferences.getString("toCurrency", "BDT") ?: "BDT",
            fromValue = sharedPreferences.getString("fromValue", "0") ?: "0",
            toValue = sharedPreferences.getString("toValue", "0") ?: "0",
            fromToExchangeRate = sharedPreferences.getString("fromToExchangeRate", "") ?: "",
            toFromExchangeRate = sharedPreferences.getString("toFromExchangeRate", "") ?: "",
            lastUpdatedInLocalTime = sharedPreferences.getString("lastUpdatedInLocalTime", "")
                ?: "",
        )
    }
}