package com.android.calculator.state.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.TipCalculatorAction
import com.android.calculator.state.TipCalculatorState
import java.text.DecimalFormat

class TipCalculatorViewModel : ViewModel() {

    var tipCalculatorState by mutableStateOf(TipCalculatorState())

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
        tipCalculatorState = tipCalculatorState.copy(bill = "0")
        calculateBill()
    }

    private fun enterDecimal() {
        if (!tipCalculatorState.bill.contains(".")) {
            tipCalculatorState = tipCalculatorState.copy(
                bill =
                if (tipCalculatorState.bill.length < 21) tipCalculatorState.bill + "."
                else tipCalculatorState.bill
            )
        }
    }

    private fun delete() {
        tipCalculatorState = tipCalculatorState.copy(
            bill =
            if (tipCalculatorState.bill == "0") tipCalculatorState.bill
            else if (tipCalculatorState.bill.length == 1) "0"
            else tipCalculatorState.bill.dropLast(1)
        )
        calculateBill()
    }

    private fun enterNumber(number: Int) {
        tipCalculatorState = tipCalculatorState.copy(
            bill =
            if (tipCalculatorState.bill == "0") number.toString()
            else if (tipCalculatorState.bill.contains(".")) {
                val parts = tipCalculatorState.bill.split(".")
                if (parts[1].length < 5 && tipCalculatorState.bill.length < 21) {
                    tipCalculatorState.bill + number.toString()
                } else {
                    tipCalculatorState.bill
                }
            } else {
                if (tipCalculatorState.bill.length < 21) {
                    tipCalculatorState.bill + number.toString()
                } else {
                    tipCalculatorState.bill
                }
            }
        )
        calculateBill()
    }

    private fun enterDoubleZero(number: String) {
        tipCalculatorState = tipCalculatorState.copy(
            bill =
            if (tipCalculatorState.bill == "0") tipCalculatorState.bill
            else if (tipCalculatorState.bill.length == 24) tipCalculatorState.bill
            else tipCalculatorState.bill + number
        )

        calculateBill()
    }

    private fun enterHeadCount(count: Int) {
        tipCalculatorState = tipCalculatorState.copy(headCount = count)
        calculateBill()
    }

    private fun enterTipPercent(percent: Int) {
        tipCalculatorState = tipCalculatorState.copy(tipPercentage = percent)
        calculateBill()
    }

    private fun calculateBill() {
        val billAmount = tipCalculatorState.bill.toDoubleOrNull() ?: 0.0
        val headCount = tipCalculatorState.headCount
        val tipPercentage = tipCalculatorState.tipPercentage

        val tipAmount = (billAmount * tipPercentage) / 100
        val totalBill = billAmount + tipAmount
        val totalPerHead = if (headCount > 0) totalBill / headCount else totalBill

        val decimalFormat = DecimalFormat("#.##")

        tipCalculatorState = tipCalculatorState.copy(
            totalBill = decimalFormat.format(totalBill),
            totalPerHead = decimalFormat.format(totalPerHead)
        )
    }
}