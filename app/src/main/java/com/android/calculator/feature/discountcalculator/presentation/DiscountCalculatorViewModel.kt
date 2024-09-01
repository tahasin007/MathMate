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
            is BaseAction.Clear -> clearCalculation()
            is BaseAction.Decimal -> enterDecimal()
            is BaseAction.Delete -> deleteLastChar()
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

    private fun clearCalculation() {
        _state.value = _state.value.copy(price = "0")
        calculateDiscount()
    }

    private fun deleteLastChar() {
        val newPrice = CommonUtils.deleteLastChar(state.value.price)

        if (newPrice != _state.value.price) {
            _state.value = _state.value.copy(price = newPrice)
            calculateDiscount()
        }
    }

    private fun enterNumber(number: String) {
        val newPrice = CommonUtils.formatUnitValues(_state.value.price, number)
        if (newPrice != _state.value.price) {
            _state.value = _state.value.copy(price = newPrice)
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