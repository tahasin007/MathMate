package com.android.calculator.feature.lenghtconverter.data.repository

import com.android.calculator.feature.lenghtconverter.data.source.LengthPreference
import com.android.calculator.feature.lenghtconverter.domain.model.LengthState
import com.android.calculator.feature.lenghtconverter.domain.repository.LengthConverterRepository
import javax.inject.Inject

class LengthConverterRepositoryImpl @Inject constructor(
    private val preferences: LengthPreference
) : LengthConverterRepository {
    override suspend fun saveLengthState(state: LengthState) {
        preferences.saveLengthState(state)
    }

    override suspend fun getLengthState(): LengthState {
        return preferences.getLengthState()
    }
}