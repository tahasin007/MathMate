package com.android.calculator.actions

import com.android.calculator.operations.CalculatorOperation

sealed class CalculatorAction {
    data class Number(val number: Int) : CalculatorAction()
    data object Clear : CalculatorAction()
    data object Delete : CalculatorAction()
    data object Decimal : CalculatorAction()
    data object Calculate : CalculatorAction()
    data class Operation(val operation: CalculatorOperation) : CalculatorAction()
}