package com.android.calculator.feature.tipcalculator.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.TipCalculatorAction
import com.android.calculator.feature.tipcalculator.domain.model.TipCalculatorState
import com.android.calculator.feature.tipcalculator.domain.repository.TipCalculatorRepository
import com.android.calculator.utils.CommonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TipCalculatorViewModel @Inject constructor(
    private val repository: TipCalculatorRepository
) : ViewModel() {

    private val _state = mutableStateOf(TipCalculatorState())
    val state: State<TipCalculatorState> = _state

    init {
        viewModelScope.launch {
            _state.value = repository.getTipCalculatorState()
        }
    }

    fun onAction(action: BaseAction) {
        when (action) {
            is TipCalculatorAction -> handleTipCalculatorAction(action)
            is BaseAction.Clear -> clearCalculation()
            is BaseAction.Decimal -> enterDecimal()
            is BaseAction.Delete -> deleteLastChar()
            is BaseAction.Number -> enterNumber(action.number.toString())
            is BaseAction.DoubleZero -> enterNumber(action.number)
            else -> {
                Log.i(TAG, "$action not implemented")
            }
        }
    }

    private fun handleTipCalculatorAction(action: TipCalculatorAction) {
        when (action) {
            is TipCalculatorAction.EnterHeadCount -> enterHeadCount(action.count)
            is TipCalculatorAction.EnterTipPercent -> enterTipPercent(action.percent)
        }
    }

    private fun clearCalculation() {
        _state.value = _state.value.copy(bill = "0")
        calculateBill()
    }

    private fun enterDecimal() {
        val bill = _state.value.bill

        if (!bill.contains(".")) {
            _state.value = _state.value.copy(bill = "$bill.")
        }
    }

    private fun deleteLastChar() {
        val newBill = CommonUtils.deleteLastChar(_state.value.bill)

        if (newBill != _state.value.bill) {
            _state.value = _state.value.copy(bill = newBill)
            calculateBill()
        }
    }

    private fun enterNumber(number: String) {
        val newBill = CommonUtils.formatUnitValues(_state.value.bill, number)
        if (newBill != _state.value.bill) {
            _state.value = _state.value.copy(bill = newBill)
            calculateBill()
        }
    }

    private fun enterHeadCount(count: Int) {
        _state.value = _state.value.copy(headCount = count)
        calculateBill()
    }

    private fun enterTipPercent(percent: Int) {
        _state.value = _state.value.copy(tipPercentage = percent)
        calculateBill()
    }

    private fun calculateBill() {
        val billAmount = _state.value.bill.toDoubleOrNull() ?: 0.0
        val headCount = _state.value.headCount
        val tipPercentage = _state.value.tipPercentage

        val tipAmount = (billAmount * tipPercentage) / 100
        val totalBill = billAmount + tipAmount
        val totalPerHead = if (headCount > 0) totalBill / headCount else totalBill

        _state.value = _state.value.copy(
            totalBill = CommonUtils.formatValue(totalBill, 2),
            totalPerHead = CommonUtils.formatValue(totalPerHead, 2)
        )
        viewModelScope.launch {
            repository.saveTipCalculatorState(_state.value)
        }
    }

    companion object {
        private const val TAG = "TipCalculatorViewModel"
    }
}