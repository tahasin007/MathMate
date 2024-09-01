package com.android.calculator.feature.numeralsystem.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.NumeralSystemAction
import com.android.calculator.feature.numeralsystem.data.repository.NumeralSystemRepositoryImpl
import com.android.calculator.feature.numeralsystem.domain.model.NumeralSystemState
import com.android.calculator.feature.numeralsystem.domain.model.NumeralSystemView
import com.android.calculator.feature.numeralsystem.presentation.utils.NumeralSystemConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NumeralSystemViewModel @Inject constructor(
    private val repository: NumeralSystemRepositoryImpl
) : ViewModel() {

    private val _numeralSystemState = mutableStateOf(NumeralSystemState())
    val numeralSystemState: State<NumeralSystemState> = _numeralSystemState

    init {
        viewModelScope.launch {
            _numeralSystemState.value = repository.getNumeralSystemState()
        }
    }

    fun onAction(action: BaseAction) {
        when (action) {
            is NumeralSystemAction -> handleNumeralSystemAction(action)
            is BaseAction.Clear -> clearCalculation()
            is BaseAction.Decimal -> enterDecimal()
            is BaseAction.Delete -> deleteLastChar()
            is BaseAction.Number -> enterNumber(action.number.toString())
            is BaseAction.DoubleZero -> enterNumber(action.number)
            else -> {}
        }
    }

    private fun handleNumeralSystemAction(action: NumeralSystemAction) {
        when (action) {
            is NumeralSystemAction.ChangeInputUnit -> {
                _numeralSystemState.value =
                    _numeralSystemState.value.copy(
                        inputUnit = action.unit,
                        inputValue = "1",
                        outputValue = "1"
                    )
            }

            is NumeralSystemAction.ChangeOutputUnit -> {
                _numeralSystemState.value =
                    _numeralSystemState.value.copy(
                        outputUnit = action.unit,
                        inputValue = "1",
                        outputValue = "1"
                    )
            }

            is NumeralSystemAction.ChangeView -> changeView()
            is NumeralSystemAction.HexSymbol -> enterNumber(action.symbol)
        }
    }

    private fun clearCalculation() {
        _numeralSystemState.value = _numeralSystemState.value.copy(
            inputValue = "0",
            outputValue = "0"
        )
        viewModelScope.launch {
            repository.saveNumeralSystemState(_numeralSystemState.value)
        }
    }

    private fun deleteLastChar() {
        _numeralSystemState.value =
            if (_numeralSystemState.value.currentView == NumeralSystemView.INPUT) {
                _numeralSystemState.value.copy(
                    inputValue =
                    if (_numeralSystemState.value.inputValue == "0") _numeralSystemState.value.inputValue
                    else if (_numeralSystemState.value.inputValue.length == 1) "0"
                    else _numeralSystemState.value.inputValue.dropLast(1)
                )
            } else {
                _numeralSystemState.value.copy(
                    outputValue =
                    if (_numeralSystemState.value.outputValue == "0") _numeralSystemState.value.outputValue
                    else if (_numeralSystemState.value.outputValue.length == 1) "0"
                    else _numeralSystemState.value.outputValue.dropLast(1)
                )
            }
        convert()
    }

    private fun changeView() {
        _numeralSystemState.value =
            if (_numeralSystemState.value.currentView == NumeralSystemView.INPUT) {
                if (_numeralSystemState.value.inputValue.last() == '.') {
                    _numeralSystemState.value.copy(
                        currentView = NumeralSystemView.OUTPUT,
                        inputValue = _numeralSystemState.value.inputValue.dropLast(1)
                    )
                } else _numeralSystemState.value.copy(currentView = NumeralSystemView.OUTPUT)
            } else {
                if (_numeralSystemState.value.outputValue.last() == '.') {
                    _numeralSystemState.value.copy(
                        currentView = NumeralSystemView.INPUT,
                        inputValue = _numeralSystemState.value.outputValue.dropLast(1)
                    )
                } else _numeralSystemState.value.copy(currentView = NumeralSystemView.INPUT)
            }
        viewModelScope.launch {
            repository.saveNumeralSystemState(_numeralSystemState.value)
        }
    }

    private fun enterDecimal() {
        if (_numeralSystemState.value.currentView == NumeralSystemView.INPUT &&
            !_numeralSystemState.value.inputValue.contains('.')
        ) {
            _numeralSystemState.value =
                _numeralSystemState.value.copy(inputValue = _numeralSystemState.value.inputValue + ".")
        } else if (_numeralSystemState.value.currentView == NumeralSystemView.OUTPUT &&
            !_numeralSystemState.value.outputValue.contains('.')
        ) {
            _numeralSystemState.value =
                _numeralSystemState.value.copy(outputValue = _numeralSystemState.value.outputValue + ".")
        }
        viewModelScope.launch {
            repository.saveNumeralSystemState(_numeralSystemState.value)
        }
    }

    private fun enterNumber(number: String) {
        _numeralSystemState.value =
            if (_numeralSystemState.value.currentView == NumeralSystemView.INPUT) {
                val inputValue = if (_numeralSystemState.value.inputValue == "0") number
                else if (_numeralSystemState.value.inputValue.length == 50) _numeralSystemState.value.inputValue
                else if (_numeralSystemState.value.inputValue.last() == '.') _numeralSystemState.value.inputValue + number
                else {
                    _numeralSystemState.value.inputValue + number
                }
                _numeralSystemState.value.copy(inputValue = inputValue)
            } else {
                val outputValue = if (_numeralSystemState.value.outputValue == "0") number
                else if (_numeralSystemState.value.outputValue.length == 50) _numeralSystemState.value.outputValue
                else if (_numeralSystemState.value.outputValue.last() == '.') _numeralSystemState.value.outputValue + number
                else {
                    _numeralSystemState.value.outputValue + number
                }
                _numeralSystemState.value.copy(outputValue = outputValue)
            }
        convert()
    }

    private fun convert() {
        val inputValue = _numeralSystemState.value.inputValue
        val outputValue = _numeralSystemState.value.outputValue
        val inputUnit = _numeralSystemState.value.inputUnit
        val outputUnit = _numeralSystemState.value.outputUnit

        _numeralSystemState.value =
            if (_numeralSystemState.value.currentView == NumeralSystemView.INPUT) {
                val convertedOutputValue =
                    NumeralSystemConverter.convert(inputValue, inputUnit, outputUnit)
                _numeralSystemState.value.copy(outputValue = convertedOutputValue)
            } else {
                val convertedInputValue =
                    NumeralSystemConverter.convert(outputValue, outputUnit, inputUnit)
                _numeralSystemState.value.copy(inputValue = convertedInputValue)
            }
        viewModelScope.launch {
            repository.saveNumeralSystemState(_numeralSystemState.value)
        }
    }
}