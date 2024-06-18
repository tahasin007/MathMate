package com.android.calculator.state.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.MassAction
import com.android.calculator.operations.CalculatorOperation
import com.android.calculator.state.MassState
import com.android.calculator.state.MassView
import com.android.calculator.utils.CommonUtils
import com.android.calculator.utils.Constants.MASS_UNITS
import com.android.calculator.utils.ExpressionEvaluator
import kotlinx.coroutines.launch

class MassViewModel : ViewModel() {

    var massState by mutableStateOf(MassState())

    fun onAction(action: BaseAction) {
        when (action) {
            is MassAction -> handleMassAction(action)
            is BaseAction.Number -> enterNumber(action.number)
            is BaseAction.Clear -> clear()
            is BaseAction.Delete -> delete()
            is BaseAction.Decimal -> enterDecimal()
            is BaseAction.Operation -> enterOperation(action.operation)
            is BaseAction.Calculate -> calculate()
            else -> {}
        }
    }

    private fun handleMassAction(action: MassAction) {
        when (action) {
            is MassAction.ChangeInputUnit -> {
                massState = massState.copy(inputUnit = action.unit)
                convert()
            }

            is MassAction.ChangeOutputUnit -> {
                massState = massState.copy(outputUnit = action.unit)
                convert()
            }

            is MassAction.ChangeView -> changeView()
            is MassAction.Convert -> convert()
        }
    }

    private fun changeView() {
        massState = if (massState.currentView == MassView.INPUT) {
            massState.copy(currentView = MassView.OUTPUT)
        } else {
            massState.copy(currentView = MassView.INPUT)
        }
        convert()
    }

    private fun convert() {
        viewModelScope.launch {
            val inputUnitFactor = MASS_UNITS[massState.inputUnit] ?: return@launch
            val outputUnitFactor = MASS_UNITS[massState.outputUnit] ?: return@launch

            if (massState.currentView == MassView.INPUT) {
                val inputValue =
                    if (massState.inputValue.isBlank() || CommonUtils.isLastCharOperator(massState.inputValue)) null
                    else calculate(massState.inputValue)?.toDoubleOrNull()
                if (inputValue == null) return@launch

                val valueInMeters = inputValue * inputUnitFactor
                val convertedValue = valueInMeters / outputUnitFactor
                massState = massState.copy(
                    outputValue =
                    if (convertedValue == 0.0) "0"
                    else if (convertedValue.toString().length == 25) massState.outputValue
                    else convertedValue.toString()
                )
            } else {
                val outputValue =
                    if (massState.outputValue.isBlank() || CommonUtils.isLastCharOperator(massState.outputValue)) return@launch
                    else calculate(massState.outputValue)?.toDoubleOrNull()
                if (outputValue == null) return@launch

                val valueInMeters = outputValue * outputUnitFactor
                val convertedValue = valueInMeters / inputUnitFactor
                massState = massState.copy(
                    inputValue =
                    if (convertedValue == 0.0) "0"
                    else if (convertedValue.toString().length == 25) massState.inputValue
                    else convertedValue.toString()
                )
            }
        }
    }

    private fun enterNumber(number: Int) {
        massState = if (massState.currentView == MassView.INPUT) {
            massState.copy(
                inputValue =

                if (massState.inputValue == "0") number.toString()
                else if (massState.inputValue.length == 25) massState.inputValue
                else massState.inputValue + number.toString()
            )
        } else {
            massState.copy(
                outputValue =
                if (massState.outputValue == "0") number.toString()
                else if (massState.outputValue.length == 25) massState.outputValue
                else massState.outputValue + number.toString()
            )
        }
        convert()
    }

    private fun clear() {
        massState = massState.copy(
            inputValue = "0",
            outputValue = "0"
        )
    }

    private fun delete() {
        massState = if (massState.currentView == MassView.INPUT) {
            massState.copy(
                inputValue =
                if (massState.inputValue == "0") massState.inputValue
                else if (massState.inputValue.length == 1) "0"
                else massState.inputValue.dropLast(1)
            )
        } else {
            massState.copy(
                outputValue =
                if (massState.outputValue == "0") massState.outputValue
                else if (massState.outputValue.length == 1) "0"
                else massState.outputValue.dropLast(1)
            )
        }
        convert()
    }

    private fun enterDecimal() {
        if (massState.currentView == MassView.INPUT && CommonUtils.canEnterDecimal(massState.inputValue)) {
            massState = if (CommonUtils.isLastCharOperator(massState.inputValue)) {
                massState.copy(
                    inputValue = massState.inputValue + "0."
                )
            } else {
                massState.copy(
                    inputValue = massState.inputValue + "."
                )
            }
        } else if (massState.currentView == MassView.OUTPUT && CommonUtils.canEnterDecimal(massState.outputValue)) {
            massState = if (CommonUtils.isLastCharOperator(massState.outputValue)) {
                massState.copy(
                    outputValue = massState.outputValue + "0."
                )
            } else {
                massState.copy(
                    outputValue = massState.outputValue + "."
                )
            }
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        massState = if (massState.currentView == MassView.INPUT) {
            massState.copy(
                inputValue =
                if (massState.inputValue == "0" ||
                    massState.inputValue.length == 25 ||
                    CommonUtils.isLastCharOperator(massState.inputValue)
                ) massState.inputValue
                else {
                    if (CommonUtils.isLastCharDecimal(massState.inputValue)) {
                        massState.inputValue + "0" + operation.symbol
                    } else massState.inputValue + operation.symbol
                }
            )
        } else {
            massState.copy(
                outputValue =
                if (massState.outputValue == "0" ||
                    massState.outputValue.length == 25 ||
                    CommonUtils.isLastCharOperator(massState.outputValue)
                ) massState.outputValue
                else {
                    if (CommonUtils.isLastCharDecimal(massState.outputValue)) {
                        massState.outputValue + "0" + operation.symbol
                    } else massState.outputValue + operation.symbol
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
        massState = if (massState.currentView == MassView.INPUT) {
            massState.copy(
                inputValue = calculate(massState.inputValue) ?: massState.inputValue
            )
        } else {
            massState.copy(
                outputValue = calculate(massState.outputValue) ?: massState.outputValue
            )
        }
    }
}