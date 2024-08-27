package com.android.calculator.feature.tipcalculator.domain.repository

import com.android.calculator.feature.tipcalculator.domain.model.TipCalculatorState

interface TipCalculatorRepository {
    suspend fun saveTipCalculatorState(state: TipCalculatorState)
    suspend fun getTipCalculatorState(): TipCalculatorState
}
