package com.android.calculator.feature.tipcalculator.presentation

data class TipCalculatorState(
    val bill: String = "0",
    val headCount: Int = 1,
    val tipPercentage: Int = 0,
    val totalBill: String = "0",
    val totalPerHead: String = "0"
)