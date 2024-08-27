package com.android.calculator.feature.massconverter.domain.repository

import com.android.calculator.feature.massconverter.domain.model.MassState

interface MassConverterRepository {
    suspend fun saveMassState(state: MassState)
    suspend fun getMassState(): MassState
}