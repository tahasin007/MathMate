package com.android.calculator.feature.tipcalculator.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.TipCalculatorAction
import com.android.calculator.feature.tipcalculator.domain.model.TipCalculatorState
import com.android.calculator.feature.tipcalculator.domain.repository.TipCalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
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
            is BaseAction.Clear -> clear()
            is BaseAction.Decimal -> enterDecimal()
            is BaseAction.Delete -> delete()
            is BaseAction.Number -> enterNumber(action.number)
            is BaseAction.DoubleZero -> enterDoubleZero(action.number)
            else -> {}
        }
    }

    private fun handleTipCalculatorAction(action: TipCalculatorAction) {
        when (action) {
            is TipCalculatorAction.EnterHeadCount -> enterHeadCount(action.count)
            is TipCalculatorAction.EnterTipPercent -> enterTipPercent(action.percent)
        }
    }

    private fun clear() {
        _state.value = _state.value.copy(bill = "0")
        calculateBill()
    }

    private fun enterDecimal() {
        if (!_state.value.bill.contains(".")) {
            _state.value = _state.value.copy(
                bill =
                if (_state.value.bill.length < 21) _state.value.bill + "."
                else _state.value.bill
            )
        }
    }

    private fun delete() {
        _state.value = _state.value.copy(
            bill =
            if (_state.value.bill == "0") _state.value.bill
            else if (_state.value.bill.length == 1) "0"
            else _state.value.bill.dropLast(1)
        )
        calculateBill()
    }

    private fun enterNumber(number: Int) {
        _state.value = _state.value.copy(
            bill =
            if (_state.value.bill == "0") number.toString()
            else if (_state.value.bill.contains(".")) {
                val parts = _state.value.bill.split(".")
                if (parts[1].length < 5 && _state.value.bill.length < 21) {
                    _state.value.bill + number.toString()
                } else {
                    _state.value.bill
                }
            } else {
                if (_state.value.bill.length < 21) {
                    _state.value.bill + number.toString()
                } else {
                    _state.value.bill
                }
            }
        )
        calculateBill()
    }

    private fun enterDoubleZero(number: String) {
        _state.value = _state.value.copy(
            bill =
            if (_state.value.bill == "0") _state.value.bill
            else if (_state.value.bill.length == 24) _state.value.bill
            else _state.value.bill + number
        )

        calculateBill()
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

        val decimalFormat = DecimalFormat("#.##")

        _state.value = _state.value.copy(
            totalBill = decimalFormat.format(totalBill),
            totalPerHead = decimalFormat.format(totalPerHead)
        )
        viewModelScope.launch {
            repository.saveTipCalculatorState(_state.value)
        }
    }
}