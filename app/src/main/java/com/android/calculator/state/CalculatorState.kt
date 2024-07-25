package com.android.calculator.state

data class CalculatorState(
    val expression: String = "",
    val result: String = "0.0",
    val isBottomSheetOpen: Boolean = false
)