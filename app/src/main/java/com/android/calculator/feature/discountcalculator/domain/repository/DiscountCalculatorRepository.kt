package com.android.calculator.feature.discountcalculator.domain.repository

import com.android.calculator.feature.discountcalculator.domain.model.DiscountCalculatorState

interface DiscountCalculatorRepository {
    suspend fun saveDiscountCalculatorState(state: DiscountCalculatorState)
    suspend fun getDiscountCalculatorState(): DiscountCalculatorState
}