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
import com.android.calculator.utils.CommonUtils
import com.android.calculator.utils.Constants.LENGTH_UNITS
import com.android.calculator.utils.ExpressionEvaluator
import kotlinx.coroutines.launch

class LengthViewModel : ViewModel() {

    var lengthState by mutableStateOf(LengthState())

    fun onAction(action: BaseAction) {
        when (action) {
            is LengthAction -> handleLengthAction(action)
            is BaseAction.Number -> enterNumber(action.number)
            is BaseAction.Clear -> clear()
            is BaseAction.Delete -> delete()
            is BaseAction.Decimal -> enterDecimal()
            is BaseAction.Operation -> enterOperation(action.operation)
            is BaseAction.Calculate -> calculate()
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

    private fun changeView() {
        lengthState = if (lengthState.currentView == LengthView.INPUT) {
            if (lengthState.inputValue.last() == '.') {
                lengthState.copy(
                    currentView = LengthView.OUTPUT,
                    inputValue = lengthState.inputValue.dropLast(1)
                )
            } else lengthState.copy(currentView = LengthView.OUTPUT)
        } else {
            if (lengthState.outputValue.last() == '.') {
                lengthState.copy(
                    currentView = LengthView.INPUT,
                    inputValue = lengthState.outputValue.dropLast(1)
                )
            } else lengthState.copy(currentView = LengthView.INPUT)
        }
    }

    private fun convert() {
        viewModelScope.launch {
            val inputUnitFactor = LENGTH_UNITS[lengthState.inputUnit] ?: return@launch
            val outputUnitFactor = LENGTH_UNITS[lengthState.outputUnit] ?: return@launch

            if (lengthState.currentView == LengthView.INPUT) {
                val inputValue =
                    if (lengthState.inputValue.isBlank() || CommonUtils.isLastCharOperator(lengthState.inputValue)) null
                    else calculate(lengthState.inputValue)?.toDoubleOrNull()
                if (inputValue == null) return@launch

                val valueInMeters = inputValue * inputUnitFactor
                val convertedValue = valueInMeters / outputUnitFactor

                val outputValue =
                    if (convertedValue == 0.0) "0"
                    else if (convertedValue.toString().length == 50) lengthState.outputValue
                    else convertedValue.toString()

                lengthState =
                    lengthState.copy(outputValue = CommonUtils.convertScientificToNormal(outputValue))
            } else {
                val outputValue =
                    if (lengthState.outputValue.isBlank() || CommonUtils.isLastCharOperator(lengthState.outputValue)) return@launch
                    else calculate(lengthState.outputValue)?.toDoubleOrNull()
                if (outputValue == null) return@launch

                val valueInMeters = outputValue * outputUnitFactor
                val convertedValue = valueInMeters / inputUnitFactor

                val inputValue =
                    if (convertedValue == 0.0) "0"
                    else if (convertedValue.toString().length == 50) lengthState.inputValue
                    else convertedValue.toString()

                lengthState =
                    lengthState.copy(inputValue = CommonUtils.convertScientificToNormal(inputValue))
            }
        }
    }

    private fun enterNumber(number: Int) {
        lengthState = if (lengthState.currentView == LengthView.INPUT) {
            val inputValue =
                if (lengthState.inputValue == "0") number.toString()
                else if (lengthState.inputValue.length == 50) lengthState.inputValue
                else if (lengthState.inputValue.last() == '.') lengthState.inputValue + number.toString()
                else {
                    CommonUtils.convertScientificToNormal(lengthState.inputValue) + number.toString()
                }
            lengthState.copy(inputValue = inputValue)
        } else {
            val outputValue =
                if (lengthState.outputValue == "0") number.toString()
                else if (lengthState.outputValue.length == 50) lengthState.outputValue
                else if (lengthState.outputValue.last() == '.') lengthState.outputValue + number.toString()
                else {
                    CommonUtils.convertScientificToNormal(lengthState.outputValue) + number.toString()
                }
            lengthState.copy(outputValue = outputValue)
        }
        convert()
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
                else if (lengthState.inputValue.length == 1) "0"
                else lengthState.inputValue.dropLast(1)
            )
        } else {
            lengthState.copy(
                outputValue =
                if (lengthState.outputValue == "0") lengthState.outputValue
                else if (lengthState.outputValue.length == 1) "0"
                else lengthState.outputValue.dropLast(1)
            )
        }
        convert()
    }

    private fun enterDecimal() {
        if (lengthState.currentView == LengthView.INPUT && CommonUtils.canEnterDecimal(lengthState.inputValue)) {
            lengthState = if (CommonUtils.isLastCharOperator(lengthState.inputValue)) {
                lengthState.copy(inputValue = lengthState.inputValue + "0.")
            } else {
                lengthState.copy(inputValue = lengthState.inputValue + ".")
            }
        } else if (lengthState.currentView == LengthView.OUTPUT &&
            CommonUtils.canEnterDecimal(lengthState.outputValue)
        ) {
            lengthState = if (CommonUtils.isLastCharOperator(lengthState.outputValue)) {
                lengthState.copy(outputValue = lengthState.outputValue + "0.")
            } else {
                lengthState.copy(outputValue = lengthState.outputValue + ".")
            }
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        lengthState = if (lengthState.currentView == LengthView.INPUT) {
            lengthState.copy(
                inputValue =
                if (lengthState.inputValue == "0" ||
                    lengthState.inputValue.length == 50 ||
                    CommonUtils.isLastCharOperator(lengthState.inputValue)
                ) lengthState.inputValue
                else {
                    if (CommonUtils.isLastCharDecimal(lengthState.inputValue)) {
                        lengthState.inputValue + "0" + operation.symbol
                    } else lengthState.inputValue + operation.symbol
                }
            )
        } else {
            lengthState.copy(
                outputValue =
                if (lengthState.outputValue == "0" ||
                    lengthState.outputValue.length == 50 ||
                    CommonUtils.isLastCharOperator(lengthState.outputValue)
                ) lengthState.outputValue
                else {
                    if (CommonUtils.isLastCharDecimal(lengthState.outputValue)) {
                        lengthState.outputValue + "0" + operation.symbol
                    } else lengthState.outputValue + operation.symbol
                }
            )
        }
        convert()
    }

    private fun calculate(expression: String): String? {
        return try {
            ExpressionEvaluator.evaluate(expression).toString()
        } catch (e: Exception) {
            null
        }
    }

    private fun calculate() {
        viewModelScope.launch {
            lengthState = if (lengthState.currentView == LengthView.INPUT) {
                val inputValue = calculate(lengthState.inputValue) ?: lengthState.inputValue
                lengthState.copy(inputValue = CommonUtils.removeZeroAfterDecimalPoint(inputValue))
            } else {
                val outputValue = calculate(lengthState.outputValue) ?: lengthState.outputValue
                lengthState.copy(outputValue = CommonUtils.removeZeroAfterDecimalPoint(outputValue))
            }
        }
    }
}