package com.android.calculator.feature.calculatormain.presentation.main

import com.android.calculator.feature.calculatormain.domain.model.Calculation

data class CalculatorMainState(
    val expression: String = "",
    val lastExpression: String = "",
    val result: String = "0",
    val recentCalculations: List<Calculation> = emptyList(),
    val isConverterSheetOpen: Boolean = false,
    val isSaveCalculationSheetOpen: Boolean = false
)