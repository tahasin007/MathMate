package com.android.calculator.feature.tipcalculator.domain.repository

import com.android.calculator.feature.tipcalculator.domain.model.TipCalculatorState

interface TipCalculatorRepository {
    fun saveTipCalculatorState(state: TipCalculatorState)
    fun getTipCalculatorState(): TipCalculatorState
}
