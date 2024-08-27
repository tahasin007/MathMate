package com.android.calculator.feature.numeralsystem.domain.repository

import com.android.calculator.feature.numeralsystem.domain.model.NumeralSystemState

interface NumeralSystemRepository {
    suspend fun saveNumeralSystemState(state: NumeralSystemState)
    suspend fun getNumeralSystemState(): NumeralSystemState
}