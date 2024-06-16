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
import com.android.calculator.utils.Constants.MASS_UNITS
import com.android.calculator.utils.ExpressionEvaluator
import kotlinx.coroutines.launch

class MassViewModel : ViewModel() {

    var massState by mutableStateOf(MassState())
    private val operators = setOf('+', '-', '*', '/', '%')

    fun onAction(action: BaseAction) {
        when (action) {
            is MassAction -> handleMassAction(action)
            is BaseAction.Number -> enterNumber(action.number)
            is BaseAction.Clear -> clear()
            is BaseAction.Delete -> delete()
            is BaseAction.Operation -> enterOperation(action.operation)
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

    private fun convert() {
        viewModelScope.launch {
            val inputUnitFactor = MASS_UNITS[massState.inputUnit] ?: return@launch
            val outputUnitFactor = MASS_UNITS[massState.outputUnit] ?: return@launch

            if (massState.currentView == MassView.INPUT) {
                val inputValue =
                    if (massState.inputValue.last() in operators) massState.inputValue.toDoubleOrNull()
                        ?: return@launch else calculate(massState.inputValue)
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
                    if (massState.outputValue.last() in operators) massState.outputValue.toDoubleOrNull()
                        ?: return@launch else calculate(massState.outputValue)
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
                else massState.inputValue.dropLast(1)
            )
        } else {
            massState.copy(
                outputValue =
                if (massState.outputValue == "0") massState.outputValue
                else massState.outputValue.dropLast(1)
            )
        }
        convert()
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

    private fun enterOperation(operation: CalculatorOperation) {
        massState = if (massState.currentView == MassView.INPUT) {
            massState.copy(
                inputValue =
                if (massState.inputValue == "0" ||
                    massState.inputValue.length == 25 ||
                    massState.inputValue.last() in operators
                ) massState.inputValue
                else massState.inputValue + operation.symbol
            )
        } else {
            massState.copy(
                outputValue =
                if (massState.outputValue == "0" ||
                    massState.outputValue.length == 25 ||
                    massState.outputValue.last() in operators
                ) massState.outputValue
                else massState.outputValue + operation.symbol
            )
        }
        convert()
    }

    private fun calculate(expression: String): Double {
        return ExpressionEvaluator.evaluate(expression)
    }

    private fun changeView() {
        massState = if (massState.currentView == MassView.INPUT) {
            massState.copy(currentView = MassView.OUTPUT)
        } else {
            massState.copy(currentView = MassView.INPUT)
        }
        convert()
    }
}