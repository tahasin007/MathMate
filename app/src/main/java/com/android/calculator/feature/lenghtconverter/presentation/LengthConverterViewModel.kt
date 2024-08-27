package com.android.calculator.feature.lenghtconverter.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.LengthAction
import com.android.calculator.feature.calculatormain.presentation.main.CalculatorOperation
import com.android.calculator.feature.lenghtconverter.data.repository.LengthConverterRepositoryImpl
import com.android.calculator.feature.lenghtconverter.domain.model.LengthState
import com.android.calculator.feature.lenghtconverter.domain.model.LengthView
import com.android.calculator.utils.CommonUtils
import com.android.calculator.utils.Constants.LENGTH_UNITS
import com.android.calculator.utils.ExpressionEvaluator
import kotlinx.coroutines.launch

class LengthConverterViewModel(private val repository: LengthConverterRepositoryImpl) :
    ViewModel() {

    private val _lengthState = mutableStateOf(LengthState())
    val lengthState: State<LengthState> = _lengthState

    init {
        viewModelScope.launch {
            _lengthState.value = repository.getLengthState()
        }
    }

    fun onAction(action: BaseAction) {
        when (action) {
            is LengthAction -> handleLengthAction(action)
            is BaseAction.Number -> enterNumber(action.number)
            is BaseAction.Clear -> clear()
            is BaseAction.Delete -> delete()
            is BaseAction.Decimal -> enterDecimal()
            is BaseAction.Operation -> enterOperation(action.operation)
            is BaseAction.Calculate -> calculate()
            is BaseAction.DoubleZero -> enterDoubleZero(action.number)
            else -> {}
        }
    }

    private fun handleLengthAction(action: LengthAction) {
        when (action) {
            is LengthAction.ChangeInputUnit -> {
                _lengthState.value = _lengthState.value.copy(inputUnit = action.unit)
                convert()
            }

            is LengthAction.ChangeOutputUnit -> {
                _lengthState.value = _lengthState.value.copy(outputUnit = action.unit)
                convert()
            }

            is LengthAction.ChangeView -> changeView()
            is LengthAction.Convert -> convert()
        }
    }

    private fun changeView() {
        _lengthState.value = if (_lengthState.value.currentView == LengthView.INPUT) {
            if (_lengthState.value.inputValue.last() == '.') {
                _lengthState.value.copy(
                    currentView = LengthView.OUTPUT,
                    inputValue = _lengthState.value.inputValue.dropLast(1)
                )
            } else _lengthState.value.copy(currentView = LengthView.OUTPUT)
        } else {
            if (_lengthState.value.outputValue.last() == '.') {
                _lengthState.value.copy(
                    currentView = LengthView.INPUT,
                    inputValue = _lengthState.value.outputValue.dropLast(1)
                )
            } else _lengthState.value.copy(currentView = LengthView.INPUT)
        }
        viewModelScope.launch { repository.saveLengthState(_lengthState.value) }
    }

    private fun convert() {
        viewModelScope.launch {
            val inputUnitFactor = LENGTH_UNITS[_lengthState.value.inputUnit] ?: return@launch
            val outputUnitFactor = LENGTH_UNITS[_lengthState.value.outputUnit] ?: return@launch

            if (_lengthState.value.currentView == LengthView.INPUT) {
                val inputValue =
                    if (_lengthState.value.inputValue.isBlank() || CommonUtils.isLastCharOperator(
                            _lengthState.value.inputValue
                        )
                    ) null
                    else calculate(_lengthState.value.inputValue)?.toDoubleOrNull()
                if (inputValue == null) return@launch

                val valueInMeters = inputValue * inputUnitFactor
                val convertedValue = valueInMeters / outputUnitFactor

                val outputValue =
                    if (convertedValue == 0.0) "0"
                    else if (convertedValue.toString().length == 50) _lengthState.value.outputValue
                    else convertedValue.toString()

                _lengthState.value =
                    _lengthState.value.copy(
                        outputValue = CommonUtils.convertScientificToNormal(
                            outputValue
                        )
                    )
            } else {
                val outputValue =
                    if (_lengthState.value.outputValue.isBlank() || CommonUtils.isLastCharOperator(
                            _lengthState.value.outputValue
                        )
                    ) return@launch
                    else calculate(_lengthState.value.outputValue)?.toDoubleOrNull()
                if (outputValue == null) return@launch

                val valueInMeters = outputValue * outputUnitFactor
                val convertedValue = valueInMeters / inputUnitFactor

                val inputValue =
                    if (convertedValue == 0.0) "0"
                    else if (convertedValue.toString().length == 50) _lengthState.value.inputValue
                    else convertedValue.toString()

                _lengthState.value =
                    _lengthState.value.copy(
                        inputValue = CommonUtils.convertScientificToNormal(inputValue)
                    )
            }
            repository.saveLengthState(_lengthState.value)
        }
    }

    private fun enterNumber(number: Int) {
        _lengthState.value = if (_lengthState.value.currentView == LengthView.INPUT) {
            val inputValue =
                if (_lengthState.value.inputValue == "0") number.toString()
                else if (_lengthState.value.inputValue.length == 50) _lengthState.value.inputValue
                else if (_lengthState.value.inputValue.last() == '.') _lengthState.value.inputValue + number.toString()
                else {
                    CommonUtils.convertScientificToNormal(_lengthState.value.inputValue) + number.toString()
                }
            _lengthState.value.copy(inputValue = inputValue)
        } else {
            val outputValue =
                if (_lengthState.value.outputValue == "0") number.toString()
                else if (_lengthState.value.outputValue.length == 50) _lengthState.value.outputValue
                else if (_lengthState.value.outputValue.last() == '.') _lengthState.value.outputValue + number.toString()
                else {
                    CommonUtils.convertScientificToNormal(_lengthState.value.outputValue) + number.toString()
                }
            _lengthState.value.copy(outputValue = outputValue)
        }
        convert()
    }

    private fun clear() {
        _lengthState.value = _lengthState.value.copy(
            inputValue = "0",
            outputValue = "0"
        )
        viewModelScope.launch { repository.saveLengthState(_lengthState.value) }
    }

    private fun delete() {
        _lengthState.value = if (_lengthState.value.currentView == LengthView.INPUT) {
            _lengthState.value.copy(
                inputValue =
                if (_lengthState.value.inputValue == "0") _lengthState.value.inputValue
                else if (_lengthState.value.inputValue.length == 1) "0"
                else _lengthState.value.inputValue.dropLast(1)
            )
        } else {
            _lengthState.value.copy(
                outputValue =
                if (_lengthState.value.outputValue == "0") _lengthState.value.outputValue
                else if (_lengthState.value.outputValue.length == 1) "0"
                else _lengthState.value.outputValue.dropLast(1)
            )
        }
        convert()
    }

    private fun enterDecimal() {
        if (_lengthState.value.currentView == LengthView.INPUT && CommonUtils.canEnterDecimal(
                _lengthState.value.inputValue
            )
        ) {
            _lengthState.value =
                if (CommonUtils.isLastCharOperator(_lengthState.value.inputValue)) {
                    _lengthState.value.copy(inputValue = _lengthState.value.inputValue + "0.")
                } else {
                    _lengthState.value.copy(inputValue = _lengthState.value.inputValue + ".")
                }
        } else if (_lengthState.value.currentView == LengthView.OUTPUT &&
            CommonUtils.canEnterDecimal(_lengthState.value.outputValue)
        ) {
            _lengthState.value =
                if (CommonUtils.isLastCharOperator(_lengthState.value.outputValue)) {
                    _lengthState.value.copy(outputValue = _lengthState.value.outputValue + "0.")
                } else {
                    _lengthState.value.copy(outputValue = _lengthState.value.outputValue + ".")
                }
        }
        viewModelScope.launch { repository.saveLengthState(_lengthState.value) }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        _lengthState.value = if (_lengthState.value.currentView == LengthView.INPUT) {
            _lengthState.value.copy(
                inputValue =
                if (_lengthState.value.inputValue == "0" ||
                    _lengthState.value.inputValue.length == 50 ||
                    CommonUtils.isLastCharOperator(_lengthState.value.inputValue)
                ) _lengthState.value.inputValue
                else {
                    if (CommonUtils.isLastCharDecimal(_lengthState.value.inputValue)) {
                        _lengthState.value.inputValue + "0" + operation.symbol
                    } else _lengthState.value.inputValue + operation.symbol
                }
            )
        } else {
            _lengthState.value.copy(
                outputValue =
                if (_lengthState.value.outputValue == "0" ||
                    _lengthState.value.outputValue.length == 50 ||
                    CommonUtils.isLastCharOperator(_lengthState.value.outputValue)
                ) _lengthState.value.outputValue
                else {
                    if (CommonUtils.isLastCharDecimal(_lengthState.value.outputValue)) {
                        _lengthState.value.outputValue + "0" + operation.symbol
                    } else _lengthState.value.outputValue + operation.symbol
                }
            )
        }
        convert()
    }

    private fun enterDoubleZero(number: String) {
        _lengthState.value = if (_lengthState.value.currentView == LengthView.INPUT) {
            val inputValue = if (_lengthState.value.inputValue == "0") _lengthState.value.inputValue
            else if (_lengthState.value.inputValue.length == 24) _lengthState.value.inputValue
            else _lengthState.value.inputValue + number
            _lengthState.value.copy(inputValue = inputValue)
        } else {
            val outputValue =
                if (_lengthState.value.outputValue == "0") _lengthState.value.outputValue
                else if (_lengthState.value.outputValue.length == 24) _lengthState.value.outputValue
                else _lengthState.value.outputValue + number
            _lengthState.value.copy(outputValue = outputValue)
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
            _lengthState.value = if (_lengthState.value.currentView == LengthView.INPUT) {
                val inputValue =
                    calculate(_lengthState.value.inputValue) ?: _lengthState.value.inputValue
                _lengthState.value.copy(
                    inputValue = CommonUtils.removeZeroAfterDecimalPoint(inputValue)
                )
            } else {
                val outputValue =
                    calculate(_lengthState.value.outputValue) ?: _lengthState.value.outputValue
                _lengthState.value.copy(
                    outputValue = CommonUtils.removeZeroAfterDecimalPoint(outputValue)
                )
            }
            repository.saveLengthState(_lengthState.value)
        }
    }
}