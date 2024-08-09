package com.android.calculator.feature.massconverter.presentation

data class MassState(
    val inputUnit: String = "Gram",
    val outputUnit: String = "Kilogram",
    val inputValue: String = "0",
    val outputValue: String = "0",
    val currentView: MassView = MassView.INPUT
)

enum class MassView {
    INPUT,
    OUTPUT
}