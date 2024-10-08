package com.android.calculator.feature.numeralsystem.data.repository

import com.android.calculator.feature.numeralsystem.data.source.NumeralSystemPreferences
import com.android.calculator.feature.numeralsystem.domain.model.NumeralSystemState
import com.android.calculator.feature.numeralsystem.domain.repository.NumeralSystemRepository
import javax.inject.Inject

class NumeralSystemRepositoryImpl @Inject constructor(
    private val preferences: NumeralSystemPreferences
) : NumeralSystemRepository {
    override suspend fun saveNumeralSystemState(state: NumeralSystemState) {
        preferences.saveNumeralSystemState(state)
    }

    override suspend fun getNumeralSystemState(): NumeralSystemState {
        return preferences.getNumeralSystemState()
    }
}