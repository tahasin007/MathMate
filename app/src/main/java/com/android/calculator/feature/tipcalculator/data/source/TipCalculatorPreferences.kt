package com.android.calculator.feature.tipcalculator.data.source

import android.content.Context
import com.android.calculator.feature.tipcalculator.domain.model.TipCalculatorState

class TipCalculatorPreferences(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("tip_calculator_prefs", Context.MODE_PRIVATE)

    fun saveTipCalculatorState(state: TipCalculatorState) {
        with(sharedPreferences.edit()) {
            putString("bill", state.bill)
            putInt("headCount", state.headCount)
            putInt("tipPercentage", state.tipPercentage)
            putString("totalBill", state.totalBill)
            putString("totalPerHead", state.totalPerHead)
            apply()
        }
    }

    fun getTipCalculatorState(): TipCalculatorState {
        return TipCalculatorState(
            bill = sharedPreferences.getString("bill", "0") ?: "0",
            headCount = sharedPreferences.getInt("headCount", 1),
            tipPercentage = sharedPreferences.getInt("tipPercentage", 0),
            totalBill = sharedPreferences.getString("totalBill", "0") ?: "0",
            totalPerHead = sharedPreferences.getString("totalPerHead", "0") ?: "0"
        )
    }
}
