package com.android.calculator.state.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.android.calculator.actions.BaseAction
import com.android.calculator.state.NumeralSystemState

class NumeralSystemViewModel : ViewModel() {

    var numeralSystemState by mutableStateOf(NumeralSystemState())

    fun onAction(action: BaseAction) {

    }
}