package com.android.calculator.actions

import com.android.calculator.operations.CalculatorOperation

sealed class CalculatorAction: BaseAction {
    data object Delete : CalculatorAction()
    data object Decimal : CalculatorAction()
    data object Parenthesis : CalculatorAction()
    data object Calculate : CalculatorAction()
    data class Operation(val operation: CalculatorOperation) : CalculatorAction()
}