package com.android.calculator.state.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.DiscountAction
import com.android.calculator.state.DiscountState

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
            is DiscountAction.EnterDiscountPercent -> enterDiscountPercent(action.percent)
        }
    }

    private fun clear() {
        discountState = discountState.copy(price = "0")
        calculateDiscount()
    }

    private fun delete() {
        discountState = discountState.copy(
            price = if (discountState.price == "0") discountState.price
            else if (discountState.price.length == 1) "0"
            else discountState.price.dropLast(1)
        )
        calculateDiscount()
    }

    private fun calculateDiscount() {
        val originalPrice = discountState.price.toDoubleOrNull() ?: 0.0
        val discountPercentage = discountState.discountPercent

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
            finalPrice = formattedDiscountedPrice, saved = formattedDiscountAmount
        )
    }

    private fun enterNumber(number: Int) {
        discountState = discountState.copy(
            price = if (discountState.price == "0") number.toString()
            else if (discountState.price.contains(".")) {
                val parts = discountState.price.split(".")
                if (parts[1].length < 5 && discountState.price.length < 21) {
                    discountState.price + number.toString()
                } else {
                    discountState.price
                }
            } else {
                if (discountState.price.length < 21) {
                    discountState.price + number.toString()
                } else {
                    discountState.price
                }
            }
        )
        calculateDiscount()
    }

    private fun enterDoubleZero(number: String) {
        discountState = discountState.copy(
            price = if (discountState.price == "0") discountState.price
            else if (discountState.price.length == 24) discountState.price
            else discountState.price + number
        )
        calculateDiscount()
    }

    private fun enterDiscountPercent(percent: Int) {
        discountState = discountState.copy(discountPercent = percent)
        calculateDiscount()
    }

    private fun enterDecimal() {
        if (!discountState.price.contains(".")) {
            discountState = discountState.copy(
                price = if (discountState.price.length < 21) discountState.price + "."
                else discountState.price
            )
        }
    }
}