package com.android.calculator.actions

import com.android.calculator.feature.calculatormain.presentation.main.CalculatorOperation

sealed interface BaseAction {
    data class Number(val number: Int) : BaseAction
    data class DoubleZero(val number: String) : BaseAction
    data object Clear : BaseAction
    data object Delete : BaseAction
    data object Decimal : BaseAction
    data class Operation(val operation: CalculatorOperation) : BaseAction
    data object Calculate : BaseAction
}