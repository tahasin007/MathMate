package com.android.calculator.state.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.LengthAction
import com.android.calculator.operations.CalculatorOperation
import com.android.calculator.state.LengthState
import com.android.calculator.state.LengthView
import com.android.calculator.utils.Constants.LENGTH_UNITS
import com.android.calculator.utils.ExpressionEvaluator
import kotlinx.coroutines.launch

class LengthViewModel : ViewModel() {

    var lengthState by mutableStateOf(LengthState())
    private val operators = setOf('+', '-', '*', '/', '%')

    fun onAction(action: BaseAction) {
        when (action) {
            is LengthAction -> handleLengthAction(action)
            is BaseAction.Number -> enterNumber(action.number)
            is BaseAction.Clear -> clear()
            is BaseAction.Delete -> delete()
            is BaseAction.Operation -> enterOperation(action.operation)
            else -> {}
        }
    }

    private fun handleLengthAction(action: LengthAction) {
        when (action) {
            is LengthAction.ChangeInputUnit -> {
                lengthState = lengthState.copy(inputUnit = action.unit)
                convert()
            }

            is LengthAction.ChangeOutputUnit -> {
                lengthState = lengthState.copy(outputUnit = action.unit)
                convert()
            }

            is LengthAction.ChangeView -> changeView()
            is LengthAction.Convert -> convert()
        }
    }

    private fun convert() {
        viewModelScope.launch {
            val inputUnitFactor = LENGTH_UNITS[lengthState.inputUnit] ?: return@launch
            val outputUnitFactor = LENGTH_UNITS[lengthState.outputUnit] ?: return@launch

            if (lengthState.currentView == LengthView.INPUT) {
                val inputValue =
                    if (lengthState.inputValue.last() in operators) lengthState.inputValue.toDoubleOrNull()
                        ?: return@launch else calculate(lengthState.inputValue)
                val valueInMeters = inputValue * inputUnitFactor
                val convertedValue = valueInMeters / outputUnitFactor
                lengthState = lengthState.copy(
                    outputValue =
                    if (convertedValue == 0.0) "0"
                    else if (convertedValue.toString().length == 25) lengthState.outputValue
                    else convertedValue.toString()
                )
            } else {
                val outputValue =
                    if (lengthState.outputValue.last() in operators) lengthState.outputValue.toDoubleOrNull()
                        ?: return@launch else calculate(lengthState.outputValue)
                val valueInMeters = outputValue * outputUnitFactor
                val convertedValue = valueInMeters / inputUnitFactor
                lengthState = lengthState.copy(
                    inputValue =
                    if (convertedValue == 0.0) "0"
                    else if (convertedValue.toString().length == 25) lengthState.inputValue
                    else convertedValue.toString()
                )
            }
        }
    }

    private fun clear() {
        lengthState = lengthState.copy(
            inputValue = "0",
            outputValue = "0"
        )
    }

    private fun delete() {
        lengthState = if (lengthState.currentView == LengthView.INPUT) {
            lengthState.copy(
                inputValue =
                if (lengthState.inputValue == "0") lengthState.inputValue
                else lengthState.inputValue.dropLast(1)
            )
        } else {
            lengthState.copy(
                outputValue =
                if (lengthState.outputValue == "0") lengthState.outputValue
                else lengthState.outputValue.dropLast(1)
            )
        }
        convert()
    }

    private fun enterNumber(number: Int) {
        lengthState = if (lengthState.currentView == LengthView.INPUT) {
            lengthState.copy(
                inputValue =
                if (lengthState.inputValue == "0") number.toString()
                else if (lengthState.inputValue.length == 25) lengthState.inputValue
                else lengthState.inputValue + number.toString()
            )
        } else {
            lengthState.copy(
                outputValue =
                if (lengthState.outputValue == "0") number.toString()
                else if (lengthState.outputValue.length == 25) lengthState.outputValue
                else lengthState.outputValue + number.toString()
            )
        }
        convert()
    }

    private fun enterOperation(operation: CalculatorOperation) {
        lengthState = if (lengthState.currentView == LengthView.INPUT) {
            lengthState.copy(
                inputValue =
                if (lengthState.inputValue == "0" ||
                    lengthState.inputValue.length == 25 ||
                    lengthState.inputValue.last() in operators
                ) lengthState.inputValue
                else lengthState.inputValue + operation.symbol
            )
        } else {
            lengthState.copy(
                outputValue =
                if (lengthState.outputValue == "0" ||
                    lengthState.outputValue.length == 25 ||
                    lengthState.outputValue.last() in operators
                ) lengthState.outputValue
                else lengthState.outputValue + operation.symbol
            )
        }
        convert()
    }

    private fun calculate(expression: String): Double {
        return ExpressionEvaluator.evaluate(expression)
    }

    private fun changeView() {
        lengthState = if (lengthState.currentView == LengthView.INPUT) {
            lengthState.copy(currentView = LengthView.OUTPUT)
        } else {
            lengthState.copy(currentView = LengthView.INPUT)
        }
    }
}