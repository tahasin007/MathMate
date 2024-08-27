package com.android.calculator.feature.numeralsystem.data.source

import android.content.Context
import android.content.SharedPreferences
import com.android.calculator.feature.numeralsystem.domain.model.NumeralSystemState
import com.android.calculator.feature.numeralsystem.domain.model.NumeralSystemView
import com.android.calculator.feature.numeralsystem.presentation.utils.NumeralSystem

class NumeralSystemPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("numeral_system_prefs", Context.MODE_PRIVATE)
    }

    fun saveNumeralSystemState(state: NumeralSystemState) {
        with(sharedPreferences.edit()) {
            putString("inputUnit", state.inputUnit)
            putString("outputUnit", state.outputUnit)
            putString("inputValue", state.inputValue)
            putString("outputValue", state.outputValue)
            putString("currentView", state.currentView.name)
            apply()
        }
    }

    fun getNumeralSystemState(): NumeralSystemState {
        val inputUnit = sharedPreferences.getString(
            "inputUnit",
            NumeralSystem.Binary::class.simpleName.toString()
        ) ?: NumeralSystem.Binary::class.simpleName.toString()
        val outputUnit = sharedPreferences.getString(
            "outputUnit",
            NumeralSystem.Decimal::class.simpleName.toString()
        ) ?: NumeralSystem.Decimal::class.simpleName.toString()
        val inputValue = sharedPreferences.getString("inputValue", "0") ?: "0"
        val outputValue = sharedPreferences.getString("outputValue", "0") ?: "0"
        val currentView = NumeralSystemView.valueOf(
            sharedPreferences.getString(
                "currentView", NumeralSystemView.INPUT.name
            ) ?: NumeralSystemView.INPUT.name
        )

        return NumeralSystemState(
            inputUnit = inputUnit,
            outputUnit = outputUnit,
            inputValue = inputValue,
            outputValue = outputValue,
            currentView = currentView
        )
    }
}
