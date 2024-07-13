package com.android.calculator.state.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.DiscountAction
import com.android.calculator.state.DiscountState
import com.android.calculator.state.DiscountView

class DiscountViewModel : ViewModel() {

    var discountState by mutableStateOf(DiscountState())

    fun onAction(action: BaseAction) {
        when (action) {
            is DiscountAction -> handleDiscountAction(action)
            is BaseAction.Clear -> clear()
            is BaseAction.Decimal -> enterDecimal()
            is BaseAction.Delete -> delete()
            is BaseAction.Number -> enterNumber(action.number)
            is BaseAction.DoubleZero -> enterDoubleZero(action.number)
            else -> {}
        }
    }

    private fun handleDiscountAction(action: DiscountAction) {
        when (action) {
            is DiscountAction.ChangeView -> changeView()
        }
    }

    private fun clear() {
        discountState = if (discountState.currentView == DiscountView.INPUT) {
            discountState.copy(inputValue = "0")
        } else {
            discountState.copy(discountValue = "0")
        }
        calculate()
    }

    private fun delete() {
        discountState = if (discountState.currentView == DiscountView.INPUT) {
            discountState.copy(
                inputValue =
                if (discountState.inputValue == "0") discountState.inputValue
                else if (discountState.inputValue.length == 1) "0"
                else discountState.inputValue.dropLast(1)
            )
        } else {
            discountState.copy(
                discountValue =
                if (discountState.discountValue == "0") discountState.discountValue
                else if (discountState.discountValue.length == 1) "0"
                else discountState.discountValue.dropLast(1)
            )
        }
        calculate()
    }

    private fun calculate() {
        val originalPrice = discountState.inputValue.toDoubleOrNull() ?: 0.0
        val discountPercentage = discountState.discountValue.toDoubleOrNull() ?: 0.0

        val discountAmount = (originalPrice * discountPercentage) / 100
        val discountedPrice = originalPrice - discountAmount

        val formattedDiscountedPrice = if (discountedPrice == discountedPrice.toInt().toDouble()) {
            discountedPrice.toInt().toString()
        } else {
            discountedPrice.toString()
        }

        val formattedDiscountAmount = if (discountAmount == discountAmount.toInt().toDouble()) {
            discountAmount.toInt().toString()
        } else {
            discountAmount.toString()
        }

        discountState = discountState.copy(
            finalValue = formattedDiscountedPrice,
            savedValue = formattedDiscountAmount
        )
    }

    private fun enterNumber(number: Int) {
        discountState = if (discountState.currentView == DiscountView.INPUT) {
            discountState.copy(
                inputValue =
                if (discountState.inputValue == "0") number.toString()
                else if (discountState.inputValue.length == 25) discountState.inputValue
                else discountState.inputValue + number.toString()
            )
        } else {
            discountState.copy(
                discountValue =
                if (discountState.discountValue == "0") number.toString()
                else if (discountState.discountValue.length == 2) discountState.discountValue
                else discountState.discountValue + number.toString()
            )
        }
        calculate()
    }

    private fun enterDoubleZero(number: String) {
        if (discountState.currentView == DiscountView.INPUT) {
            discountState = discountState.copy(
                inputValue =
                if (discountState.inputValue == "0") discountState.inputValue
                else if (discountState.inputValue.length == 24) discountState.inputValue
                else discountState.inputValue + number
            )
        }
        calculate()
    }

    private fun enterDecimal() {
        if (discountState.currentView == DiscountView.INPUT &&
            !discountState.inputValue.contains(".")
        ) {
            discountState = discountState.copy(
                inputValue =
                if (discountState.inputValue.length == 25) discountState.inputValue
                else discountState.inputValue + "."
            )
        }
    }

    private fun changeView() {
        discountState = if (discountState.currentView == DiscountView.INPUT) {
            if (discountState.inputValue.last() == '.') {
                discountState.copy(
                    currentView = DiscountView.DISCOUNT,
                    inputValue = discountState.inputValue.dropLast(1)
                )
            } else {
                discountState.copy(currentView = DiscountView.DISCOUNT)
            }
        } else {
            discountState.copy(currentView = DiscountView.INPUT)
        }
    }
}