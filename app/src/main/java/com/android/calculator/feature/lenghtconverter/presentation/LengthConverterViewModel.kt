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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LengthConverterViewModel @Inject constructor(
    private val repository: LengthConverterRepositoryImpl
) : ViewModel() {

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
            is BaseAction.Number -> enterNumber(action.number.toString())
            is BaseAction.Clear -> clearCalculation()
            is BaseAction.Delete -> deleteLastChar()
            is BaseAction.Decimal -> enterDecimal()
            is BaseAction.Operation -> enterOperation(action.operation)
            is BaseAction.Calculate -> calculate()
            is BaseAction.DoubleZero -> enterNumber(action.number)
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
            if (_lengthState.value.inputValue.last() == '.' ||
                CommonUtils.isLastCharOperator(_lengthState.value.inputValue)
            ) {
                _lengthState.value.copy(
                    currentView = LengthView.OUTPUT,
                    inputValue = _lengthState.value.inputValue.dropLast(1)
                )
            } else _lengthState.value.copy(currentView = LengthView.OUTPUT)
        } else {
            if (_lengthState.value.outputValue.last() == '.' ||
                CommonUtils.isLastCharOperator(_lengthState.value.outputValue)
            ) {
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
                    if (_lengthState.value.inputValue.isBlank() ||
                        CommonUtils.isLastCharOperator(_lengthState.value.inputValue)
                    ) null
                    else calculate(_lengthState.value.inputValue)
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
                    if (_lengthState.value.outputValue.isBlank() ||
                        CommonUtils.isLastCharOperator(_lengthState.value.outputValue)
                    ) return@launch
                    else calculate(_lengthState.value.outputValue)
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

    private fun enterNumber(number: String) {
        if (_lengthState.value.currentView == LengthView.INPUT) {
            val inputValue = CommonUtils.formatAndCombine(_lengthState.value.inputValue, number)
            if (inputValue != _lengthState.value.inputValue) {
                _lengthState.value = _lengthState.value.copy(inputValue = inputValue)
                convert()
            }
        } else {
            val outputValue = CommonUtils.formatAndCombine(_lengthState.value.outputValue, number)
            if (outputValue != _lengthState.value.outputValue) {
                _lengthState.value = _lengthState.value.copy(outputValue = outputValue)
                convert()
            }
        }
    }

    private fun clearCalculation() {
        _lengthState.value = _lengthState.value.copy(
            inputValue = "0",
            outputValue = "0"
        )
        viewModelScope.launch { repository.saveLengthState(_lengthState.value) }
    }

    private fun deleteLastChar() {
        if (_lengthState.value.currentView == LengthView.INPUT) {
            val newInputValue =
                CommonUtils.deleteLastCharFromExpression(_lengthState.value.inputValue)
            if (newInputValue != _lengthState.value.inputValue) {
                _lengthState.value = _lengthState.value.copy(inputValue = newInputValue)
                convert()
            }
        } else {
            val outputValue =
                CommonUtils.deleteLastCharFromExpression(_lengthState.value.outputValue)
            if (outputValue != _lengthState.value.outputValue) {
                _lengthState.value = _lengthState.value.copy(outputValue = outputValue)
                convert()
            }
        }
    }

    private fun enterDecimal() {
        if (_lengthState.value.currentView == LengthView.INPUT &&
            CommonUtils.canEnterDecimal(_lengthState.value.inputValue)
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
                    CommonUtils.isLastCharOperator(_lengthState.value.outputValue)
                ) _lengthState.value.outputValue
                else {
                    if (CommonUtils.isLastCharDecimal(_lengthState.value.outputValue)) {
                        _lengthState.value.outputValue + "0" + operation.symbol
                    } else _lengthState.value.outputValue + operation.symbol
                }
            )
        }
        viewModelScope.launch { repository.saveLengthState(_lengthState.value) }
    }

    private fun calculate(expression: String): Double? {
        return try {
            ExpressionEvaluator.evaluate(expression)
        } catch (e: Exception) {
            null
        }
    }

    private fun calculate() {
        viewModelScope.launch {
            if (_lengthState.value.currentView == LengthView.INPUT) {
                val inputValue = calculate(_lengthState.value.inputValue)
                if (inputValue != null && inputValue.toString() != _lengthState.value.inputValue) {
                    _lengthState.value = _lengthState.value.copy(
                        inputValue = CommonUtils.formatValue(inputValue)
                    )
                    repository.saveLengthState(_lengthState.value)
                }
            } else {
                val outputValue = calculate(_lengthState.value.outputValue)
                if (outputValue != null && outputValue.toString() != _lengthState.value.outputValue) {
                    _lengthState.value = _lengthState.value.copy(
                        outputValue = CommonUtils.formatValue(outputValue)
                    )
                    repository.saveLengthState(_lengthState.value)
                }
            }
        }
    }
}