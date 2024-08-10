package com.android.calculator.feature.discountcalculator.data.source

import android.content.Context
import com.android.calculator.feature.discountcalculator.domain.model.DiscountCalculatorState

class DiscountCalculatorPreferences(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("discount_prefs", Context.MODE_PRIVATE)

    fun saveDiscountCalculatorState(state: DiscountCalculatorState) {
        with(sharedPreferences.edit()) {
            putString("price", state.price)
            putInt("discountPercent", state.discountPercent)
            putString("finalPrice", state.finalPrice)
            putString("saved", state.saved)
            apply()
        }
    }

    fun getDiscountCalculatorState(): DiscountCalculatorState {
        return DiscountCalculatorState(
            price = sharedPreferences.getString("price", "0") ?: "0",
            discountPercent = sharedPreferences.getInt("discountPercent", 0),
            finalPrice = sharedPreferences.getString("finalPrice", "0") ?: "0",
            saved = sharedPreferences.getString("saved", "0") ?: "0"
        )
    }
}