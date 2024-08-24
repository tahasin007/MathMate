package com.android.calculator.feature.currencyconverter.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.actions.BaseAction
import com.android.calculator.feature.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.android.calculator.feature.currencyconverter.domain.model.CurrencyRate
import kotlinx.coroutines.launch

class CurrencyConverterViewModel(
    private val repository: CurrencyRepositoryImpl
) : ViewModel() {
    private val _state = mutableStateOf(repository.getCurrencyRates())
    val state: State<CurrencyRate> = _state

    private val _baseCurrency = mutableStateOf("USD")
    val baseCurrency: State<String> = _baseCurrency

    private val _targetCurrency = mutableStateOf("BDT")
    val targetCurrency: State<String> = _targetCurrency

    init {
        viewModelScope.launch {
            try {
//                repository.fetchAndSaveCurrencyRates("USD")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onAction(action: BaseAction) {

    }
}