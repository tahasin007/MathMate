package com.android.calculator.feature.calculatormain.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.CalculatorAction
import com.android.calculator.feature.calculatormain.domain.model.Bookmark
import com.android.calculator.feature.calculatormain.domain.model.Calculation
import com.android.calculator.feature.calculatormain.domain.usecase.CalculationUseCases
import com.android.calculator.utils.CommonUtils
import com.android.calculator.utils.ExpressionEvaluator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorMainViewModel @Inject constructor(
    private val calculationUseCases: CalculationUseCases
) : ViewModel() {

    private companion object {
        const val RECENT_CALCULATION_LIMIT = 10
    }

    var calculatorState by mutableStateOf(CalculatorMainState())

    init {
        observeRecentCalculations()
    }

    private fun observeRecentCalculations() {
        viewModelScope.launch {
            calculationUseCases.getCalculations()
                .map { calculations -> calculations.take(RECENT_CALCULATION_LIMIT) }
                .collect { recentCalculations ->
                    calculatorState = calculatorState.copy(recentCalculations = recentCalculations)
                }
        }
    }

    private fun resetToIdleState() {
        calculatorState = calculatorState.copy(
            expression = "",
            lastExpression = "",
            result = "0",
            isSaveCalculationSheetOpen = false
        )
    }

    fun onAction(action: BaseAction) {
        when (action) {
            is CalculatorAction -> handleCalculatorAction(action)
            is BaseAction.Number -> enterNumber(action.number)
            is BaseAction.DoubleZero -> enterDoubleZero(action.number)
            is BaseAction.Clear -> clearCalculation()
            is BaseAction.Delete -> deleteLastChar()
            is BaseAction.Calculate -> calculate()
            is BaseAction.Operation -> enterOperation(action.operation)
            is BaseAction.Decimal -> enterDecimal()
            else -> {}
        }
    }

    private fun handleCalculatorAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Parenthesis -> enterParenthesis()
            is CalculatorAction.ConverterMenuVisibility -> openConverterBottomSheet(action.isSheetOpen)
            is CalculatorAction.SaveCalculationMenuVisibility -> openSaveCalculationBottomSheet(
                action.isSheetOpen
            )

            is CalculatorAction.SaveBookmark -> saveBookmark(action.name)
        }
    }

    private fun calculate() {
        val expression = calculatorState.expression
        if (expression.isBlank()) {
            resetToIdleState()
            return
        }

        val openCount = expression.count { it == '(' }
        val closeCount = expression.count { it == ')' }
        val parenthesisDiff = openCount - closeCount

        val filteredExpression = when {
            CommonUtils.isLastCharOperator(expression) -> {
                expression.dropLast(1)
            }

            parenthesisDiff > 0 -> {
                expression + ")".repeat(parenthesisDiff)
            }

            else -> expression
        }
        val result = ExpressionEvaluator.evaluate(filteredExpression)
        calculatorState = calculatorState.copy(
            expression = CommonUtils.removeZeroAfterDecimalPoint(result),
            result = CommonUtils.removeZeroAfterDecimalPoint(result),
            lastExpression = filteredExpression
        )
        saveCalculation(filteredExpression)
    }

    private fun deleteLastChar() {
        if (calculatorState.expression.isBlank()) return

        val updatedExpression = calculatorState.expression.dropLast(1)
        if (updatedExpression.isBlank()) {
            resetToIdleState()
            return
        }

        calculatorState = calculatorState.copy(expression = updatedExpression)
    }

    private fun clearCalculation() {
        resetToIdleState()
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
        // After = is pressed the result becomes the expression; start fresh.
        val base = if (calculatorState.expression.isNotEmpty() &&
            calculatorState.result == calculatorState.expression
        ) "" else calculatorState.expression

        // Isolate the number segment that is currently being typed
        val lastSepIndex = base.indexOfLast { it in "+-*/%(" }
        val currentNumber = base.substring(lastSepIndex + 1)

        // If the current segment is "0", replace it so we never get "09", "099", etc.
        val updated = if (currentNumber == "0") {
            base.dropLast(1) + number.toString()
        } else {
            base + number.toString()
        }

        calculatorState = calculatorState.copy(expression = updated)
    }

    private fun enterDoubleZero(number: String) {
        val base = if (calculatorState.expression.isNotEmpty() &&
            calculatorState.result == calculatorState.expression
        ) "" else calculatorState.expression

        val lastSepIndex = base.indexOfLast { it in "+-*/%(" }
        val currentNumber = base.substring(lastSepIndex + 1)

        if (currentNumber.isEmpty() || currentNumber == "0") return

        calculatorState = calculatorState.copy(expression = base + number)
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (calculatorState.expression.isBlank() ||
            CommonUtils.isLastCharOperator(calculatorState.expression)
        ) return

        val updatedExpression =
            if (calculatorState.expression.last() == '.')
                calculatorState.expression + "0" + operation.symbol
            else calculatorState.expression + operation.symbol

        calculatorState = calculatorState.copy(
            expression = updatedExpression
        )
    }

    private fun enterParenthesis() {
        val expression = calculatorState.expression
        val openCount = expression.count { it == '(' }
        val closeCount = expression.count { it == ')' }

        val lastChar = expression.lastOrNull()

        val parenthesis = when {
            lastChar == null || lastChar in "+-*/%(" -> "("
            openCount > closeCount -> ")"
            CommonUtils.isLastCharNumber(expression) -> "*("
            else -> "("
        }

        val updatedExpression = expression + parenthesis
        calculatorState = calculatorState.copy(
            expression = updatedExpression
        )
    }

    private fun openConverterBottomSheet(sheetOpen: Boolean) {
        calculatorState = calculatorState.copy(
            isConverterSheetOpen = sheetOpen
        )
    }

    private fun openSaveCalculationBottomSheet(sheetOpen: Boolean) {
        calculatorState = calculatorState.copy(
            isSaveCalculationSheetOpen = sheetOpen
        )
    }

    private fun saveBookmark(name: String) {
        viewModelScope.launch {
            calculationUseCases.insertBookmark(
                Bookmark(
                    name = name,
                    expression = calculatorState.lastExpression,
                    result = calculatorState.result,
                    date = System.currentTimeMillis()
                )
            )
        }
    }

    private fun saveCalculation(filteredExpression: String) {
        viewModelScope.launch {
            calculationUseCases.insertCalculation(
                Calculation(
                    expression = filteredExpression,
                    result = calculatorState.result,
                    date = System.currentTimeMillis()
                )
            )
        }
    }
}