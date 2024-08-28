package com.android.calculator.feature.discountcalculator.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.DiscountAction
import com.android.calculator.feature.discountcalculator.data.repository.DiscountCalculatorRepositoryImpl
import com.android.calculator.feature.discountcalculator.domain.model.DiscountCalculatorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
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
        _state.value = _state.value.copy(price = "0")
        calculateDiscount()
    }

    private fun delete() {
        _state.value = _state.value.copy(
            price = if (_state.value.price == "0") _state.value.price
            else if (_state.value.price.length == 1) "0"
            else _state.value.price.dropLast(1)
        )
        calculateDiscount()
    }

    private fun enterNumber(number: Int) {
        _state.value = _state.value.copy(
            price = if (_state.value.price == "0") number.toString()
            else if (_state.value.price.contains(".")) {
                val parts = _state.value.price.split(".")
                if (parts[1].length < 5 && _state.value.price.length < 21) {
                    _state.value.price + number.toString()
                } else {
                    _state.value.price
                }
            } else {
                if (_state.value.price.length < 21) {
                    _state.value.price + number.toString()
                } else {
                    _state.value.price
                }
            }
        )
        calculateDiscount()
    }

    private fun enterDoubleZero(number: String) {
        _state.value = _state.value.copy(
            price = if (_state.value.price == "0") _state.value.price
            else if (_state.value.price.length == 24) _state.value.price
            else _state.value.price + number
        )
        calculateDiscount()
    }

    private fun enterDiscountPercent(percent: Int) {
        _state.value = _state.value.copy(discountPercent = percent)
        calculateDiscount()
    }

    private fun enterDecimal() {
        if (!_state.value.price.contains(".")) {
            _state.value = _state.value.copy(
                price = if (_state.value.price.length < 21) _state.value.price + "."
                else _state.value.price
            )
        }
    }

    private fun calculateDiscount() {
        val originalPrice = _state.value.price.toDoubleOrNull() ?: 0.0
        val discountPercentage = _state.value.discountPercent

        val discountAmount = (originalPrice * discountPercentage) / 100
        val discountedPrice = originalPrice - discountAmount

        val decimalFormat = DecimalFormat("#.##")

        _state.value = _state.value.copy(
            finalPrice = decimalFormat.format(discountedPrice),
            saved = decimalFormat.format(discountAmount)
        )
        viewModelScope.launch {
            repository.saveDiscountCalculatorState(_state.value)
        }
    }
}