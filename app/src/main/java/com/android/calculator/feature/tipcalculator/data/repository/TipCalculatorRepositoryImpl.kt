package com.android.calculator.feature.tipcalculator.data.repository

import com.android.calculator.feature.tipcalculator.data.source.TipCalculatorPreferences
import com.android.calculator.feature.tipcalculator.domain.model.TipCalculatorState
import com.android.calculator.feature.tipcalculator.domain.repository.TipCalculatorRepository
import javax.inject.Inject

class TipCalculatorRepositoryImpl @Inject constructor(
    private val preferences: TipCalculatorPreferences
) : TipCalculatorRepository {

    override suspend fun saveTipCalculatorState(state: TipCalculatorState) {
        preferences.saveTipCalculatorState(state)
    }

    override suspend fun getTipCalculatorState(): TipCalculatorState {
        return preferences.getTipCalculatorState()
    }
}
