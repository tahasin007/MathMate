package com.android.calculator.feature.numeralsystem.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.NumeralSystemAction
import com.android.calculator.utils.NumeralSystemConverter

class NumeralSystemViewModel : ViewModel() {

    var numeralSystemState by mutableStateOf(NumeralSystemState())

    fun onAction(action: BaseAction) {
        when (action) {
            is NumeralSystemAction -> handleNumeralSystemAction(action)
            is BaseAction.Clear -> clear()
            is BaseAction.Decimal -> enterDecimal()
            is BaseAction.Delete -> delete()
            is BaseAction.Number -> enterNumber(action.number.toString())
            else -> {}
        }
    }

    private fun handleNumeralSystemAction(action: NumeralSystemAction) {
        when (action) {
            is NumeralSystemAction.ChangeInputUnit -> {
                numeralSystemState = numeralSystemState.copy(inputUnit = action.unit)
                Log.i("NumeralSystemViewModel", "$numeralSystemState")
                convert()
            }

            is NumeralSystemAction.ChangeOutputUnit -> {
                numeralSystemState = numeralSystemState.copy(outputUnit = action.unit)
                Log.i("NumeralSystemViewModel", "$numeralSystemState")
                convert()
            }

            is NumeralSystemAction.ChangeView -> changeView()
            is NumeralSystemAction.HexSymbol -> enterNumber(action.symbol)
        }
    }

    private fun clear() {
        numeralSystemState = numeralSystemState.copy(
            inputValue = "0",
            outputValue = "0"
        )
    }

    private fun delete() {
        numeralSystemState = if (numeralSystemState.currentView == NumeralSystemView.INPUT) {
            numeralSystemState.copy(
                inputValue =
                if (numeralSystemState.inputValue == "0") numeralSystemState.inputValue
                else if (numeralSystemState.inputValue.length == 1) "0"
                else numeralSystemState.inputValue.dropLast(1)
            )
        } else {
            numeralSystemState.copy(
                outputValue =
                if (numeralSystemState.outputValue == "0") numeralSystemState.outputValue
                else if (numeralSystemState.outputValue.length == 1) "0"
                else numeralSystemState.outputValue.dropLast(1)
            )
        }
        convert()
    }

    private fun changeView() {
        numeralSystemState = if (numeralSystemState.currentView == NumeralSystemView.INPUT) {
            if (numeralSystemState.inputValue.last() == '.') {
                numeralSystemState.copy(
                    currentView = NumeralSystemView.OUTPUT,
                    inputValue = numeralSystemState.inputValue.dropLast(1)
                )
            } else numeralSystemState.copy(currentView = NumeralSystemView.OUTPUT)
        } else {
            if (numeralSystemState.outputValue.last() == '.') {
                numeralSystemState.copy(
                    currentView = NumeralSystemView.INPUT,
                    inputValue = numeralSystemState.outputValue.dropLast(1)
                )
            } else numeralSystemState.copy(currentView = NumeralSystemView.INPUT)
        }
    }

    private fun enterDecimal() {

    }

    private fun enterNumber(number: String) {
        numeralSystemState = if (numeralSystemState.currentView == NumeralSystemView.INPUT) {
            val inputValue = if (numeralSystemState.inputValue == "0") number
            else if (numeralSystemState.inputValue.length == 50) numeralSystemState.inputValue
            else if (numeralSystemState.inputValue.last() == '.') numeralSystemState.inputValue + number
            else {
                numeralSystemState.inputValue + number
            }
            numeralSystemState.copy(inputValue = inputValue)
        } else {
            val outputValue = if (numeralSystemState.outputValue == "0") number
            else if (numeralSystemState.outputValue.length == 50) numeralSystemState.outputValue
            else if (numeralSystemState.outputValue.last() == '.') numeralSystemState.outputValue + number
            else {
                numeralSystemState.outputValue + number
            }
            numeralSystemState.copy(outputValue = outputValue)
        }
        convert()
    }

    private fun convert() {
        val inputValue = numeralSystemState.inputValue
        val outputValue = numeralSystemState.outputValue
        val inputUnit = numeralSystemState.inputUnit
        val outputUnit = numeralSystemState.outputUnit

        numeralSystemState = if (numeralSystemState.currentView == NumeralSystemView.INPUT) {
            val convertedOutputValue =
                NumeralSystemConverter.convert(inputValue, inputUnit, outputUnit)
            numeralSystemState.copy(outputValue = convertedOutputValue)
        } else {
            val convertedInputValue =
                NumeralSystemConverter.convert(outputValue, outputUnit, inputUnit)
            numeralSystemState.copy(inputValue = convertedInputValue)
        }
    }
}