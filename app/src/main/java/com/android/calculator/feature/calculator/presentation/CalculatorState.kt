package com.android.calculator.feature.calculator.presentation

data class CalculatorState(
    val expression: String = "",
    val result: String = "0.0",
    val isBottomSheetOpen: Boolean = false
)