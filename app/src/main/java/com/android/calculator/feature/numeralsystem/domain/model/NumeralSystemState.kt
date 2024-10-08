package com.android.calculator.feature.numeralsystem.domain.model

import com.android.calculator.feature.numeralsystem.presentation.utils.NumeralSystem

data class NumeralSystemState(
    val inputUnit: String = NumeralSystem.Binary::class.simpleName.toString(),
    val outputUnit: String = NumeralSystem.Decimal::class.simpleName.toString(),
    val inputValue: String = "1",
    val outputValue: String = "1",
    val currentView: NumeralSystemView = NumeralSystemView.INPUT
)