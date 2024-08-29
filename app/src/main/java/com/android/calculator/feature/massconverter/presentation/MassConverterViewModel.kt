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
            if (_massState.value.inputValue.last() == '.') {
                _massState.value.copy(
                    currentView = MassView.OUTPUT,
                    inputValue = _massState.value.inputValue.dropLast(1)
                )
            } else _massState.value.copy(currentView = MassView.OUTPUT)
        } else {
            if (_massState.value.outputValue.last() == '.') {
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
                    else calculate(_massState.value.inputValue)?.toDoubleOrNull()
                if (inputValue == null) return@launch

                val valueInMeters = inputValue * inputUnitFactor
                val convertedValue = valueInMeters / outputUnitFactor

                val outputValue =
                    if (convertedValue == 0.0) "0"
                    else if (convertedValue.toString().length == 50) _massState.value.outputValue
                    else convertedValue.toString()

                _massState.value =
                    _massState.value.copy(
                        outputValue = CommonUtils.convertScientificToNormal(outputValue)
                    )
            } else {
                val outputValue =
                    if (_massState.value.outputValue.isBlank() || CommonUtils.isLastCharOperator(
                            _massState.value.outputValue
                        )
                    ) return@launch
                    else calculate(_massState.value.outputValue)?.toDoubleOrNull()
                if (outputValue == null) return@launch

                val valueInMeters = outputValue * outputUnitFactor
                val convertedValue = valueInMeters / inputUnitFactor

                val inputValue =
                    if (convertedValue == 0.0) "0"
                    else if (convertedValue.toString().length == 50) _massState.value.inputValue
                    else convertedValue.toString()

                _massState.value =
                    _massState.value.copy(
                        inputValue = CommonUtils.convertScientificToNormal(inputValue)
                    )
            }
            repository.saveMassState(_massState.value)
        }
    }

    private fun enterNumber(number: Int) {
        _massState.value = if (_massState.value.currentView == MassView.INPUT) {
            val inputValue =
                if (_massState.value.inputValue == "0") number.toString()
                else if (_massState.value.inputValue.length == 50) _massState.value.inputValue
                else if (_massState.value.inputValue.last() == '.') _massState.value.inputValue + number.toString()
                else {
                    CommonUtils.convertScientificToNormal(_massState.value.inputValue) + number.toString()
                }
            _massState.value.copy(inputValue = inputValue)
        } else {
            val outputValue =
                if (_massState.value.outputValue == "0") number.toString()
                else if (_massState.value.outputValue.length == 50) _massState.value.outputValue
                else if (_massState.value.outputValue.last() == '.') _massState.value.outputValue + number.toString()
                else {
                    CommonUtils.convertScientificToNormal(_massState.value.outputValue) + number.toString()
                }
            _massState.value.copy(outputValue = outputValue)
        }
        convert()
    }

    private fun clear() {
        _massState.value = _massState.value.copy(
            inputValue = "0",
            outputValue = "0"
        )
        viewModelScope.launch { repository.saveMassState(_massState.value) }
    }

    private fun delete() {
        _massState.value = if (_massState.value.currentView == MassView.INPUT) {
            _massState.value.copy(
                inputValue =
                if (_massState.value.inputValue == "0") _massState.value.inputValue
                else if (_massState.value.inputValue.length == 1) "0"
                else _massState.value.inputValue.dropLast(1)
            )
        } else {
            _massState.value.copy(
                outputValue =
                if (_massState.value.outputValue == "0") _massState.value.outputValue
                else if (_massState.value.outputValue.length == 1) "0"
                else _massState.value.outputValue.dropLast(1)
            )
        }
        convert()
    }

    private fun enterDecimal() {
        if (_massState.value.currentView == MassView.INPUT && CommonUtils.canEnterDecimal(_massState.value.inputValue)) {
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
                    _massState.value.inputValue.length == 50 ||
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
                    _massState.value.outputValue.length == 50 ||
                    CommonUtils.isLastCharOperator(_massState.value.outputValue)
                ) _massState.value.outputValue
                else {
                    if (CommonUtils.isLastCharDecimal(_massState.value.outputValue)) {
                        _massState.value.outputValue + "0" + operation.symbol
                    } else _massState.value.outputValue + operation.symbol
                }
            )
        }
        convert()
    }

    private fun enterDoubleZero(number: String) {
        _massState.value = if (_massState.value.currentView == MassView.INPUT) {
            val inputValue = if (_massState.value.inputValue == "0") _massState.value.inputValue
            else if (_massState.value.inputValue.length == 24) _massState.value.inputValue
            else _massState.value.inputValue + number
            _massState.value.copy(inputValue = inputValue)
        } else {
            val outputValue = if (_massState.value.outputValue == "0") _massState.value.outputValue
            else if (_massState.value.outputValue.length == 24) _massState.value.outputValue
            else _massState.value.outputValue + number
            _massState.value.copy(outputValue = outputValue)
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
            _massState.value = if (_massState.value.currentView == MassView.INPUT) {
                val inputValue =
                    calculate(_massState.value.inputValue) ?: _massState.value.inputValue
                _massState.value.copy(
                    inputValue = CommonUtils.removeZeroAfterDecimalPoint(inputValue)
                )
            } else {
                val outputValue =
                    calculate(_massState.value.outputValue) ?: _massState.value.outputValue
                _massState.value.copy(
                    outputValue = CommonUtils.removeZeroAfterDecimalPoint(outputValue)
                )
            }
            repository.saveMassState(_massState.value)
        }
    }
}