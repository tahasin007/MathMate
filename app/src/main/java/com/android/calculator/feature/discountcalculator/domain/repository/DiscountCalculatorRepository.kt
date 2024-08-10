package com.android.calculator.feature.discountcalculator.domain.repository

import com.android.calculator.feature.discountcalculator.domain.model.DiscountCalculatorState

interface DiscountCalculatorRepository {
    fun saveDiscountCalculatorState(state: DiscountCalculatorState)
    fun getDiscountCalculatorState(): DiscountCalculatorState
}