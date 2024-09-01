package com.android.calculator.feature.massconverter.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.MassAction
import com.android.calculator.feature.calculatormain.presentation.main.CalculatorOperation
import com.android.calculator.feature.massconverter.data.repository.MassConverterRepositoryImpl
import com.android.calculator.feature.massconverter.domain.model.MassState
import com.android.calculator.feature.massconverter.domain.model.MassView
import com.android.calculator.utils.CommonUtils
import com.android.calculator.utils.Constants.MASS_UNITS
import com.android.calculator.utils.ExpressionEvaluator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MassConverterViewModel @Inject constructor(
    private val repository: MassConverterRepositoryImpl
) : ViewModel() {

    private val _massState = mutableStateOf(MassState())
    val massState: State<MassState> = _massState

    init {
        viewModelScope.launch {
            _massState.value = repository.getMassState()
        }
    }

    fun onAction(action: BaseAction) {
        when (action) {
            is MassAction -> handleMassAction(action)
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

    private fun handleMassAction(action: MassAction) {
        when (action) {
            is MassAction.ChangeInputUnit -> {
                _massState.value = _massState.value.copy(inputUnit = action.unit)
                convert()
            }

            is MassAction.ChangeOutputUnit -> {
                _massState.value = _massState.value.copy(outputUnit = action.unit)
                convert()
            }

            is MassAction.ChangeView -> changeView()
            is MassAction.Convert -> convert()
        }
    }

    private fun changeView() {
        _massState.value = if (_massState.value.currentView == MassView.INPUT) {
            if (_massState.value.inputValue.last() == '.' ||
                CommonUtils.isLastCharOperator(_massState.value.inputValue)
            ) {
                _massState.value.copy(
                    currentView = MassView.OUTPUT,
                    inputValue = _massState.value.inputValue.dropLast(1)
                )
            } else _massState.value.copy(currentView = MassView.OUTPUT)
        } else {
            if (_massState.value.outputValue.last() == '.' ||
                CommonUtils.isLastCharOperator(_massState.value.outputValue)
            ) {
                _massState.value.copy(
                    currentView = MassView.INPUT,
                    inputValue = _massState.value.outputValue.dropLast(1)
                )
            } else _massState.value.copy(currentView = MassView.INPUT)
        }
        viewModelScope.launch { repository.saveMassState(_massState.value) }
    }

    private fun convert() {
        viewModelScope.launch {
            val inputUnitFactor = MASS_UNITS[_massState.value.inputUnit] ?: return@launch
            val outputUnitFactor = MASS_UNITS[_massState.value.outputUnit] ?: return@launch

            if (_massState.value.currentView == MassView.INPUT) {
                val inputValue =
                    if (_massState.value.inputValue.isBlank() ||
                        CommonUtils.isLastCharOperator(_massState.value.inputValue)
                    ) null
                    else calculate(_massState.value.inputValue)
                if (inputValue == null) return@launch

                val valueInMeters = inputValue * inputUnitFactor
                val convertedValue = valueInMeters / outputUnitFactor

                val outputValue =
                    if (convertedValue == 0.0) "0"
                    else convertedValue.toString()

                _massState.value =
                    _massState.value.copy(
                        outputValue = CommonUtils.convertScientificToNormal(outputValue)
                    )
            } else {
                val outputValue =
                    if (_massState.value.outputValue.isBlank() ||
                        CommonUtils.isLastCharOperator(_massState.value.outputValue)
                    ) return@launch
                    else calculate(_massState.value.outputValue)
                if (outputValue == null) return@launch

                val valueInMeters = outputValue * outputUnitFactor
                val convertedValue = valueInMeters / inputUnitFactor

                val inputValue =
                    if (convertedValue == 0.0) "0"
                    else convertedValue.toString()

                _massState.value =
                    _massState.value.copy(
                        inputValue = CommonUtils.convertScientificToNormal(inputValue)
                    )
            }
            repository.saveMassState(_massState.value)
        }
    }

    private fun enterNumber(number: String) {
        if (_massState.value.currentView == MassView.INPUT) {
            val inputValue = CommonUtils.formatAndCombine(_massState.value.inputValue, number)
            if (inputValue != _massState.value.inputValue) {
                _massState.value = _massState.value.copy(inputValue = inputValue)
                convert()
            }
        } else {
            val outputValue = CommonUtils.formatAndCombine(_massState.value.outputValue, number)
            if (outputValue != _massState.value.outputValue) {
                _massState.value = _massState.value.copy(outputValue = outputValue)
                convert()
            }
        }
    }

    private fun clearCalculation() {
        _massState.value = _massState.value.copy(
            inputValue = "0",
            outputValue = "0"
        )
        viewModelScope.launch { repository.saveMassState(_massState.value) }
    }

    private fun deleteLastChar() {
        if (_massState.value.currentView == MassView.INPUT) {
            val newInputValue =
                CommonUtils.deleteLastCharFromExpression(_massState.value.inputValue)
            if (newInputValue != _massState.value.inputValue) {
                _massState.value = _massState.value.copy(inputValue = newInputValue)
                convert()
            }
        } else {
            val outputValue =
                CommonUtils.deleteLastCharFromExpression(_massState.value.outputValue)
            if (outputValue != _massState.value.outputValue) {
                _massState.value = _massState.value.copy(outputValue = outputValue)
                convert()
            }
        }
    }

    private fun enterDecimal() {
        if (_massState.value.currentView == MassView.INPUT &&
            CommonUtils.canEnterDecimal(_massState.value.inputValue)
        ) {
            _massState.value = if (CommonUtils.isLastCharOperator(_massState.value.inputValue)) {
                _massState.value.copy(inputValue = _massState.value.inputValue + "0.")
            } else {
                _massState.value.copy(inputValue = _massState.value.inputValue + ".")
            }
        } else if (_massState.value.currentView == MassView.OUTPUT &&
            CommonUtils.canEnterDecimal(_massState.value.outputValue)
        ) {
            _massState.value = if (CommonUtils.isLastCharOperator(_massState.value.outputValue)) {
                _massState.value.copy(outputValue = _massState.value.outputValue + "0.")
            } else {
                _massState.value.copy(outputValue = _massState.value.outputValue + ".")
            }
        }
        viewModelScope.launch { repository.saveMassState(_massState.value) }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        _massState.value = if (_massState.value.currentView == MassView.INPUT) {
            _massState.value.copy(
                inputValue =
                if (_massState.value.inputValue == "0" ||
                    CommonUtils.isLastCharOperator(_massState.value.inputValue)
                ) _massState.value.inputValue
                else {
                    if (CommonUtils.isLastCharDecimal(_massState.value.inputValue)) {
                        _massState.value.inputValue + "0" + operation.symbol
                    } else _massState.value.inputValue + operation.symbol
                }
            )
        } else {
            _massState.value.copy(
                outputValue =
                if (_massState.value.outputValue == "0" ||
                    CommonUtils.isLastCharOperator(_massState.value.outputValue)
                ) _massState.value.outputValue
                else {
                    if (CommonUtils.isLastCharDecimal(_massState.value.outputValue)) {
                        _massState.value.outputValue + "0" + operation.symbol
                    } else _massState.value.outputValue + operation.symbol
                }
            )
        }
        viewModelScope.launch { repository.saveMassState(_massState.value) }
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
            if (_massState.value.currentView == MassView.INPUT) {
                val inputValue = calculate(_massState.value.inputValue)
                if (inputValue != null && inputValue.toString() != _massState.value.inputValue) {
                    _massState.value =
                        _massState.value.copy(inputValue = CommonUtils.formatValue(inputValue))
                    repository.saveMassState(_massState.value)
                }
            } else {
                val outputValue = calculate(_massState.value.outputValue)
                if (outputValue != null && outputValue.toString() != _massState.value.outputValue) {
                    _massState.value =
                        _massState.value.copy(outputValue = CommonUtils.formatValue(outputValue))
                    repository.saveMassState(_massState.value)
                }
            }
        }
    }
}