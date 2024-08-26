package com.android.calculator.feature.currencyconverter.presentation

data class CurrencyState(
    val fromCurrency: String = "USD",
    val toCurrency: String = "BDT",
    val fromValue: String = "0",
    val toValue: String = "0",
    val fromToExchangeRate: String = "",
    val toFromExchangeRate: String = "",
    val lastUpdatedInLocalTime: String = "",
    val currentView: CurrencyView = CurrencyView.FROM
)

enum class CurrencyView {
    FROM,
    TO
}