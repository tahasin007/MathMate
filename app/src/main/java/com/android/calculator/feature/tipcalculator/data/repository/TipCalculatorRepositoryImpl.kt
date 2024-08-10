package com.android.calculator.feature.tipcalculator.data.repository

import com.android.calculator.feature.tipcalculator.data.source.TipCalculatorPreferences
import com.android.calculator.feature.tipcalculator.domain.repository.TipCalculatorRepository
import com.android.calculator.feature.tipcalculator.domain.model.TipCalculatorState

class TipCalculatorRepositoryImpl(private val preferences: TipCalculatorPreferences) :
    TipCalculatorRepository {

    override fun saveTipCalculatorState(state: TipCalculatorState) {
        preferences.saveTipCalculatorState(state)
    }

    override fun getTipCalculatorState(): TipCalculatorState {
        return preferences.getTipCalculatorState()
    }
}
