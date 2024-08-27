package com.android.calculator.feature.massconverter.data.source

import android.content.Context
import android.content.SharedPreferences
import com.android.calculator.feature.massconverter.domain.model.MassState
import com.android.calculator.feature.massconverter.domain.model.MassView

class MassPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("mass_prefs", Context.MODE_PRIVATE)
    }

    fun saveMassState(state: MassState) {
        with(sharedPreferences.edit()) {
            putString("inputUnit", state.inputUnit)
            putString("outputUnit", state.outputUnit)
            putString("inputValue", state.inputValue)
            putString("outputValue", state.outputValue)
            putString("currentView", state.currentView.name)
            apply()
        }
    }

    fun getMassState(): MassState {
        return MassState(
            inputUnit = sharedPreferences.getString("inputUnit", "Gram") ?: "Gram",
            outputUnit = sharedPreferences.getString("outputUnit", "Kilogram") ?: "Kilogram",
            inputValue = sharedPreferences.getString("inputValue", "0") ?: "0",
            outputValue = sharedPreferences.getString("outputValue", "0") ?: "0",
            currentView = MassView.valueOf(
                sharedPreferences.getString(
                    "currentView",
                    MassView.INPUT.name
                ) ?: MassView.INPUT.name
            )
        )
    }
}
