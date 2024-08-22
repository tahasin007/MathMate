package com.android.calculator.feature.calculatormain.presentation.main

data class CalculatorMainState(
    val expression: String = "",
    val result: String = "0.0",
    val isConverterSheetOpen: Boolean = false,
    val isSaveCalculationSheetOpen: Boolean = false
)