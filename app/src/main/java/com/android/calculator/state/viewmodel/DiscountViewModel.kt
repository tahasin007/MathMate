package com.android.calculator.state.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.android.calculator.actions.BaseAction
import com.android.calculator.state.DiscountState

class DiscountViewModel : ViewModel() {

    var discountState by mutableStateOf(DiscountState())

    fun onAction(action: BaseAction) {
        when(action) {
            BaseAction.Clear -> TODO()
            BaseAction.Decimal -> TODO()
            BaseAction.Delete -> TODO()
            else -> {}
        }
    }
}