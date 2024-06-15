package com.android.calculator.actions

sealed class CalculatorAction: BaseAction {
    data object Parenthesis : CalculatorAction()
}