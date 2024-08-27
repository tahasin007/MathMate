package com.android.calculator.feature.lenghtconverter.domain.repository

import com.android.calculator.feature.lenghtconverter.domain.model.LengthState

interface LengthConverterRepository {
    suspend fun saveLengthState(state: LengthState)
    suspend fun getLengthState(): LengthState
}