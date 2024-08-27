package com.android.calculator.feature.numeralsystem.domain.repository

import com.android.calculator.feature.numeralsystem.domain.model.NumeralSystemState

interface NumeralSystemRepository {
    fun saveNumeralSystemState(state: NumeralSystemState)
    fun getNumeralSystemState(): NumeralSystemState
}