package com.android.calculator.state.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.LengthAction
import com.android.calculator.state.LengthState
import com.android.calculator.state.LengthView
import kotlinx.coroutines.launch

class LengthViewModel : ViewModel() {

    var lengthState by mutableStateOf(LengthState())

    private val conversionFactors = mapOf(
        "Meter" to 1.0,
        "Kilometer" to 1000.0,
        "Centimeter" to 0.01,
        "Millimeter" to 0.001,
        "Micrometer" to 0.000001,
        "Nanometer" to 0.000000001,
        "Mile" to 1609.34,
        "Yard" to 0.9144,
        "Foot" to 0.3048,
        "Inch" to 0.0254
    )

    fun onAction(action: BaseAction) {
        when (action) {
            is LengthAction -> handleLengthAction(action)
            is BaseAction.Number -> enterNumber(action.number)
            is BaseAction.Clear -> clear()
            else -> {}
        }
    }

    private fun handleLengthAction(action: LengthAction) {
        when (action) {
            is LengthAction.SelectInputUnit -> {
                lengthState = lengthState.copy(inputUnit = action.unit)
                if (lengthState.currentView == LengthView.INPUT) {
                    convert()
                }
            }

            is LengthAction.SelectOutputUnit -> {
                lengthState = lengthState.copy(outputUnit = action.unit)
                if (lengthState.currentView == LengthView.OUTPUT) {
                    convert()
                }
            }

            is LengthAction.ChangeView -> changeView()
            is LengthAction.Convert -> convert()
        }
    }

    private fun convert() {
        viewModelScope.launch {
            val inputUnitFactor = conversionFactors[lengthState.inputUnit] ?: return@launch
            val outputUnitFactor = conversionFactors[lengthState.outputUnit] ?: return@launch

            if (lengthState.currentView == LengthView.INPUT) {
                val inputValue = lengthState.inputValue.toDoubleOrNull() ?: return@launch
                val valueInMeters = inputValue * inputUnitFactor
                val convertedValue = valueInMeters / outputUnitFactor
                lengthState = lengthState.copy(outputValue = convertedValue.toString())
            } else {
                val outputValue = lengthState.outputValue.toDoubleOrNull() ?: return@launch
                val valueInMeters = outputValue * outputUnitFactor
                val convertedValue = valueInMeters / inputUnitFactor
                lengthState = lengthState.copy(inputValue = convertedValue.toString())
            }
        }
    }

    private fun clear() {
        lengthState = lengthState.copy(
            inputValue = "0",
            outputValue = "0"
        )
    }

    private fun enterNumber(number: Int) {
        lengthState = if (lengthState.currentView == LengthView.INPUT) {
            lengthState.copy(
                inputValue = if (lengthState.inputValue != "0") lengthState.inputValue + number.toString()
                else number.toString()
            )
        } else {
            lengthState.copy(
                outputValue = if (lengthState.outputValue != "0") lengthState.outputValue + number.toString()
                else number.toString()
            )
        }
        convert()
    }

    private fun changeView() {
        lengthState = if (lengthState.currentView == LengthView.INPUT) {
            lengthState.copy(currentView = LengthView.OUTPUT)
        } else {
            lengthState.copy(currentView = LengthView.INPUT)
        }
    }
}