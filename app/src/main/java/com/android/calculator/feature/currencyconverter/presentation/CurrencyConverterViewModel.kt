package com.android.calculator.feature.currencyconverter.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.CurrencyAction
import com.android.calculator.feature.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.android.calculator.feature.currencyconverter.domain.model.CurrencyState
import com.android.calculator.feature.currencyconverter.domain.model.CurrencyView
import com.android.calculator.feature.currencyconverter.presentation.utils.CurrencyUtils
import com.android.calculator.utils.CommonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val repository: CurrencyRepositoryImpl
) : ViewModel() {

    private val _currencyRate = mutableStateOf(CurrencyUtils.defaultCurrencyRate)
//    val currencyRate: State<CurrencyRate> = _currencyRate

    private val _currencyState = mutableStateOf(CurrencyState())
    val currencyState: State<CurrencyState> = _currencyState

    init {
        val job = viewModelScope.launch {
            try {
                repository.fetchAndSaveCurrencyRates("USD")
                _currencyRate.value = repository.getCurrencyRates()
                _currencyState.value = repository.getCurrencyState()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        job.invokeOnCompletion { throwable ->
            if (throwable == null) {
                updateExchangeRates()
            }
        }
    }

    fun onAction(action: BaseAction) {
        when (action) {
            is CurrencyAction -> handleCurrencyAction(action)
            is BaseAction.Number -> enterNumber(action.number.toString())
            is BaseAction.Clear -> clearCalculation()
            is BaseAction.Delete -> deleteLastChar()
            is BaseAction.Decimal -> enterDecimal()
            is BaseAction.DoubleZero -> enterNumber(action.number)
            else -> {}
        }
    }

    private fun handleCurrencyAction(action: CurrencyAction) {
        when (action) {
            is CurrencyAction.ChangeFromUnit -> {
                _currencyState.value = _currencyState.value.copy(fromCurrency = action.unit)
                convert()
                updateExchangeRates()
            }

            is CurrencyAction.ChangeToUnit -> {
                _currencyState.value = _currencyState.value.copy(toCurrency = action.unit)
                convert()
                updateExchangeRates()
            }

            is CurrencyAction.ChangeView -> changeView()
            is CurrencyAction.SwitchView -> switchView()
            is CurrencyAction.Convert -> {}
        }
    }

    private fun enterNumber(number: String) {
        if (_currencyState.value.currentView == CurrencyView.FROM) {
            val fromValue = CommonUtils.formatUnitValues(_currencyState.value.fromValue, number)
            if (fromValue != _currencyState.value.fromValue) {
                _currencyState.value = _currencyState.value.copy(fromValue = fromValue)
                convert()
            }
        } else {
            val toValue = CommonUtils.formatUnitValues(_currencyState.value.toValue, number)
            if (toValue != _currencyState.value.toValue) {
                _currencyState.value = _currencyState.value.copy(toValue = toValue)
                convert()
            }
        }
    }

    private fun clearCalculation() {
        _currencyState.value = _currencyState.value.copy(
            fromValue = "0",
            toValue = "0"
        )
        viewModelScope.launch {
            repository.saveCurrencyState(_currencyState.value)
        }
    }

    private fun deleteLastChar() {
        if (_currencyState.value.currentView == CurrencyView.FROM) {
            val newFromValue = CommonUtils.deleteLastChar(_currencyState.value.fromValue)
            if (newFromValue != _currencyState.value.fromValue) {
                _currencyState.value = _currencyState.value.copy(fromValue = newFromValue)
                convert()
            }
        } else {
            val newToValue = CommonUtils.deleteLastChar(_currencyState.value.toValue)
            if (newToValue != _currencyState.value.toValue) {
                _currencyState.value = _currencyState.value.copy(toValue = newToValue)
                convert()
            }
        }
    }

    private fun enterDecimal() {
        if (_currencyState.value.currentView == CurrencyView.FROM &&
            CommonUtils.canEnterDecimal(_currencyState.value.fromValue)
        ) {
            _currencyState.value =
                if (CommonUtils.isLastCharOperator(_currencyState.value.fromValue)) {
                    _currencyState.value.copy(fromValue = _currencyState.value.fromValue + "0.")
                } else {
                    _currencyState.value.copy(fromValue = _currencyState.value.fromValue + ".")
                }
        } else if (_currencyState.value.currentView == CurrencyView.TO &&
            CommonUtils.canEnterDecimal(_currencyState.value.toValue)
        ) {
            _currencyState.value =
                if (CommonUtils.isLastCharOperator(_currencyState.value.toValue)) {
                    _currencyState.value.copy(toValue = _currencyState.value.toValue + "0.")
                } else {
                    _currencyState.value.copy(toValue = _currencyState.value.toValue + ".")
                }
        }
        viewModelScope.launch {
            repository.saveCurrencyState(_currencyState.value)
        }
    }

    private fun changeView() {
        _currencyState.value = if (_currencyState.value.currentView == CurrencyView.FROM) {
            if (_currencyState.value.fromValue.last() == '.') {
                _currencyState.value.copy(
                    currentView = CurrencyView.TO,
                    fromValue = _currencyState.value.fromValue.dropLast(1)
                )
            } else _currencyState.value.copy(currentView = CurrencyView.TO)
        } else {
            if (_currencyState.value.toValue.last() == '.') {
                _currencyState.value.copy(
                    currentView = CurrencyView.FROM,
                    toValue = _currencyState.value.toValue.dropLast(1)
                )
            } else _currencyState.value.copy(currentView = CurrencyView.FROM)
        }
        viewModelScope.launch {
            repository.saveCurrencyState(_currencyState.value)
        }
    }

    private fun switchView() {
        _currencyState.value = _currencyState.value.copy(
            fromValue = _currencyState.value.toValue,
            toValue = _currencyState.value.fromValue,
            fromCurrency = _currencyState.value.toCurrency,
            toCurrency = _currencyState.value.fromCurrency,
            fromToExchangeRate = _currencyState.value.toFromExchangeRate,
            toFromExchangeRate = _currencyState.value.fromToExchangeRate
        )
        viewModelScope.launch {
            repository.saveCurrencyState(_currencyState.value)
        }
    }


    private fun convert() {
        // Get the current state values
        val currentRate = _currencyRate.value
        val currentState = _currencyState.value

        val fromCurrency = currentState.fromCurrency
        val toCurrency = currentState.toCurrency

        val fromRate = currentRate.conversion_rates[fromCurrency]
        val toRate = currentRate.conversion_rates[toCurrency]

        if (fromRate != null && toRate != null) {
            when (currentState.currentView) {
                CurrencyView.FROM -> {
                    // Convert from 'fromValue' to 'toValue'
                    val fromValue = currentState.fromValue.toDoubleOrNull() ?: 0.0
                    val amountInBaseCurrency = fromValue / fromRate
                    val convertedValue = amountInBaseCurrency * toRate
                    _currencyState.value =
                        currentState.copy(toValue = CommonUtils.formatValue(convertedValue))
                }

                CurrencyView.TO -> {
                    // Convert from 'toValue' to 'fromValue'
                    val toValue = currentState.toValue.toDoubleOrNull() ?: 0.0
                    val amountInBaseCurrency = toValue / toRate
                    val convertedValue = amountInBaseCurrency * fromRate
                    _currencyState.value =
                        currentState.copy(fromValue = CommonUtils.formatValue(convertedValue))
                }
            }
        } else {
            // Handle the case where rates are missing
            _currencyState.value = currentState.copy(
                toValue = "0",
                fromValue = "0"
            )
        }
        viewModelScope.launch {
            repository.saveCurrencyState(_currencyState.value)
        }
    }

    private fun updateExchangeRates() {
        val fromCurrency = _currencyState.value.fromCurrency
        val toCurrency = _currencyState.value.toCurrency
        val rate = _currencyRate.value

        val fromRate = rate.conversion_rates[fromCurrency]
        val toRate = rate.conversion_rates[toCurrency]

        if (fromRate != null && toRate != null) {
            val fromToExchangeRate = toRate / fromRate
            val toFromExchangeRate = fromRate / toRate

            _currencyState.value = _currencyState.value.copy(
                fromToExchangeRate = "1 $fromCurrency = ${formatExchangeRate(fromToExchangeRate)} $toCurrency",
                toFromExchangeRate = "1 $toCurrency = ${formatExchangeRate(toFromExchangeRate)} $fromCurrency"
            )
        }

        _currencyState.value = _currencyState.value.copy(
            lastUpdatedInLocalTime = CurrencyUtils.convertToLocalTime(rate.time_last_update_utc)
        )
    }

    private fun formatExchangeRate(rate: Double): String {
        return if (rate >= 1) {
            String.format(Locale.US, "%.2f", rate)
        } else {
            String.format(Locale.US, "%.3f", rate)
        }
    }
}