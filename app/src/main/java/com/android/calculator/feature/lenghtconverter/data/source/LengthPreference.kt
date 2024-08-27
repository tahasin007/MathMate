package com.android.calculator.feature.lenghtconverter.data.source

import android.content.Context
import com.android.calculator.feature.lenghtconverter.domain.model.LengthState
import com.android.calculator.feature.lenghtconverter.domain.model.LengthView

class LengthPreference(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("length_prefs", Context.MODE_PRIVATE)

    fun saveLengthState(state: LengthState) {
        with(sharedPreferences.edit()) {
            putString("inputUnit", state.inputUnit)
            putString("outputUnit", state.outputUnit)
            putString("inputValue", state.inputValue)
            putString("outputValue", state.outputValue)
            putString("currentView", state.currentView.name)
            apply()
        }
    }

    fun getLengthState(): LengthState {
        return LengthState(
            inputUnit = sharedPreferences.getString("inputUnit", "Meter") ?: "Meter",
            outputUnit = sharedPreferences.getString("outputUnit", "Kilometer") ?: "Kilometer",
            inputValue = sharedPreferences.getString("inputValue", "0") ?: "0",
            outputValue = sharedPreferences.getString("outputValue", "0") ?: "0",
            currentView = LengthView.valueOf(
                sharedPreferences.getString(
                    "currentView",
                    LengthView.INPUT.name
                ) ?: LengthView.INPUT.name
            )
        )
    }
}