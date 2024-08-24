package com.android.calculator.feature.currencyconverter.domain.model

data class CurrencyRate(
    val time_last_update_utc: String,
    val base_code: String,
    val conversion_rates: Map<String, Double>
)