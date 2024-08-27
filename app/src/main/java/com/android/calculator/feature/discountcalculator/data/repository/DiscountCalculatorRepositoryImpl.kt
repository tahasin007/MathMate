package com.android.calculator.feature.discountcalculator.data.repository

import com.android.calculator.feature.discountcalculator.data.source.DiscountCalculatorPreferences
import com.android.calculator.feature.discountcalculator.domain.model.DiscountCalculatorState
import com.android.calculator.feature.discountcalculator.domain.repository.DiscountCalculatorRepository

class DiscountCalculatorRepositoryImpl(private val preferences: DiscountCalculatorPreferences) :
    DiscountCalculatorRepository {
    override suspend fun saveDiscountCalculatorState(state: DiscountCalculatorState) {
        preferences.saveDiscountCalculatorState(state)
    }

    override suspend fun getDiscountCalculatorState(): DiscountCalculatorState {
        return preferences.getDiscountCalculatorState()
    }
}