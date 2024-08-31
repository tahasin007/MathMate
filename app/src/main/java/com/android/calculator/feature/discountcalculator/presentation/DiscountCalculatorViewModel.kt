package com.android.calculator.feature.discountcalculator.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.DiscountAction
import com.android.calculator.feature.discountcalculator.data.repository.DiscountCalculatorRepositoryImpl
import com.android.calculator.feature.discountcalculator.domain.model.DiscountCalculatorState
import com.android.calculator.utils.CommonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscountCalculatorViewModel @Inject constructor(
    private val repository: DiscountCalculatorRepositoryImpl
) : ViewModel() {

    private val _state = mutableStateOf(DiscountCalculatorState())
    val state: State<DiscountCalculatorState> = _state

    init {
        viewModelScope.launch {
            _state.value = repository.getDiscountCalculatorState()
        }
    }

    fun onAction(action: BaseAction) {
        when (action) {
            is DiscountAction -> handleDiscountAction(action)
            is BaseAction.Clear -> clear()
            is BaseAction.Decimal -> enterDecimal()
            is BaseAction.Delete -> delete()
            is BaseAction.Number -> enterNumber(action.number.toString())
            is BaseAction.DoubleZero -> enterNumber(action.number)
            else -> {}
        }
    }

    private fun handleDiscountAction(action: DiscountAction) {
        when (action) {
            is DiscountAction.EnterDiscountPercent -> enterDiscountPercent(action.percent)
        }
    }

    private fun clear() {
        _state.value = _state.value.copy(price = "0")
        calculateDiscount()
    }

    private fun delete() {
        val normalNotation = CommonUtils.convertScientificToNormal(_state.value.price)

        val newBill = when {
            normalNotation.length == 1 -> "0"
            else -> normalNotation.dropLast(1)
        }

        val isLastCharDecimal = newBill.endsWith('.')

        _state.value = _state.value.copy(
            price = CommonUtils.formatValue(newBill.toDouble(), 10)
                    + if (isLastCharDecimal) "." else ""
        )

        calculateDiscount()
    }

    private fun enterNumber(number: String) {
        val isLastCharDecimal = _state.value.price.endsWith('.')
        val normalNotation = CommonUtils.convertScientificToNormal2(_state.value.price)

        val decimalPart = normalNotation.substringAfter('.', "")
        if (decimalPart.length >= 10) {
            return // Don't add more digits if there are already 10 digits after the decimal
        }

        val newValueString = if (isLastCharDecimal) "$normalNotation.$number"
        else normalNotation + number

        val newBill = newValueString.toDoubleOrNull()

        if (newBill != null && newBill <= CommonUtils.MAX_LIMIT && newBill >= CommonUtils.MIN_LIMIT) {
            val formattedValue = CommonUtils.formatValue(newBill, 10)

            val finalValue = if (newValueString.contains(".") && !newValueString.contains("E")) {
                newValueString // Keep original string with trailing zeros if it contains a decimal point
            } else {
                formattedValue
            }

            _state.value = _state.value.copy(price = finalValue)
            calculateDiscount()
        }
    }

    private fun enterDiscountPercent(percent: Int) {
        _state.value = _state.value.copy(discountPercent = percent)
        calculateDiscount()
    }

    private fun enterDecimal() {
        val price = _state.value.price

        if (!price.contains(".")) {
            _state.value = _state.value.copy(price = "$price.")
        }
    }

    private fun calculateDiscount() {
        val originalPrice = _state.value.price.toDoubleOrNull() ?: 0.0
        val discountPercentage = _state.value.discountPercent

        val discountAmount = (originalPrice * discountPercentage) / 100
        val discountedPrice = originalPrice - discountAmount

        _state.value = _state.value.copy(
            finalPrice = CommonUtils.formatValue(discountedPrice, 2),
            saved = CommonUtils.formatValue(discountAmount, 2)
        )
        viewModelScope.launch {
            repository.saveDiscountCalculatorState(_state.value)
        }
    }
}