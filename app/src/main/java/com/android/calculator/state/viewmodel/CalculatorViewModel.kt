package com.android.calculator.state.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.CalculatorAction
import com.android.calculator.operations.CalculatorOperation
import com.android.calculator.state.CalculatorState
import com.android.calculator.utils.ExpressionEvaluator

class CalculatorViewModel : ViewModel() {

    var calculatorState by mutableStateOf(CalculatorState())

    fun onAction(action: BaseAction) {
        when (action) {
            is CalculatorAction -> handleCalculatorAction(action)
            is BaseAction.Number -> enterNumber(action.number)
            is BaseAction.DoubleZero -> enterDoubleZero(action.number)
            is BaseAction.Clear -> clear()
            is BaseAction.Delete -> delete()
            is BaseAction.Calculate -> calculate()
            is BaseAction.Operation -> enterOperation(action.operation)
            is BaseAction.Decimal -> enterDecimal()
            else -> {}
        }
    }

    private fun handleCalculatorAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Parenthesis -> enterParenthesis()
            is CalculatorAction.BottomSheetVisibility -> openConverterBottomSheet()
            else -> {}
        }
    }

    private fun calculate() {
        val result = ExpressionEvaluator.evaluate(calculatorState.expression)
        calculatorState = calculatorState.copy(
            expression = result.toString(),
            result = result.toString()
        )
    }

    private fun delete() {
        if (calculatorState.expression.isBlank()) return

        calculatorState = calculatorState.copy(
            expression = calculatorState.expression.dropLast(1)
        )
    }

    private fun clear() {
        calculatorState = CalculatorState()
    }

    private fun enterDecimal() {
        if (calculatorState.expression.isBlank() || !calculatorState.expression.last().isDigit()) {
            return
        }

        // Check if the current number already contains a decimal point
        val lastNumberIndex =
            calculatorState.expression.lastIndexOfAny(charArrayOf('+', '-', '*', '/', '%'))
        val lastNumber = calculatorState.expression.substring(lastNumberIndex + 1)
        if (lastNumber.contains('.')) {
            return
        }

        val updatedExpression = calculatorState.expression + "."
        calculatorState = calculatorState.copy(
            expression = updatedExpression
        )
    }

    private fun enterNumber(number: Int) {
        val updatedExpression =
            if (calculatorState.expression.isNotEmpty() &&
                calculatorState.result == calculatorState.expression
            ) {
                number.toString()
            } else {
                calculatorState.expression + number.toString()
            }

        calculatorState = calculatorState.copy(
            expression = updatedExpression
        )
    }

    private fun enterDoubleZero(number: String) {
        val updatedExpression =
            if (calculatorState.expression.isNotEmpty() &&
                calculatorState.result == calculatorState.expression
            ) {
                number
            } else {
                calculatorState.expression + number
            }

        calculatorState = calculatorState.copy(
            expression = updatedExpression
        )
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (calculatorState.expression.isBlank()) return

        val updatedExpression =
            if (calculatorState.expression.last() == '.')
                calculatorState.expression + "0" + operation.symbol
            else calculatorState.expression + operation.symbol

        calculatorState = calculatorState.copy(
            expression = updatedExpression
        )
    }

    private fun enterParenthesis() {

    }

    private fun openConverterBottomSheet() {
        calculatorState = calculatorState.copy(
            isBottomSheetOpen = calculatorState.isBottomSheetOpen.not()
        )
    }
}