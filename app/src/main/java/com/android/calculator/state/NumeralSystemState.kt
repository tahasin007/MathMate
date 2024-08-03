package com.android.calculator.state

data class NumeralSystemState(
    val inputUnit: String = "Binary",
    val outputUnit: String = "Octal",
    val inputValue: String = "0",
    val outputValue: String = "0",
    val currentView: NumeralSystemView = NumeralSystemView.INPUT
)

enum class NumeralSystemView {
    INPUT,
    OUTPUT
}