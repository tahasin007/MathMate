package com.android.calculator.feature.currencyconverter.data.source

import android.content.Context
import android.content.SharedPreferences
import com.android.calculator.feature.currencyconverter.domain.model.CurrencyRate
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
}