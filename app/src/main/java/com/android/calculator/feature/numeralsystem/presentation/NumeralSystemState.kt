package com.android.calculator.feature.numeralsystem.presentation

import com.android.calculator.utils.NumeralSystem

data class NumeralSystemState(
    val inputUnit: String = NumeralSystem.Binary::class.simpleName.toString(),
    val outputUnit: String = NumeralSystem.Decimal::class.simpleName.toString(),
    val inputValue: String = "0",
    val outputValue: String = "0",
    val currentView: NumeralSystemView = NumeralSystemView.INPUT
)

enum class NumeralSystemView {
    INPUT,
    OUTPUT
}