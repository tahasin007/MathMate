package com.android.calculator.feature.calculator.presentation

sealed class CalculatorOperation(val symbol: String) {
    data object Add : CalculatorOperation("+")
    data object Subtract : CalculatorOperation("-")
    data object Multiply : CalculatorOperation("*")
    data object Divide : CalculatorOperation("/")
    data object Mod : CalculatorOperation("%")
    data object Parenthesis : CalculatorOperation("(")
}