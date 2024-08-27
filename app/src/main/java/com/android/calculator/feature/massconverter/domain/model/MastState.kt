package com.android.calculator.feature.massconverter.domain.model

data class MassState(
    val inputUnit: String = "Gram",
    val outputUnit: String = "Kilogram",
    val inputValue: String = "0",
    val outputValue: String = "0",
    val currentView: MassView = MassView.INPUT
)