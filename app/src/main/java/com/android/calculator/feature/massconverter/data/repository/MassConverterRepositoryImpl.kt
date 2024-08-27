package com.android.calculator.feature.massconverter.data.repository

import com.android.calculator.feature.massconverter.data.source.MassPreferences
import com.android.calculator.feature.massconverter.domain.model.MassState
import com.android.calculator.feature.massconverter.domain.repository.MassConverterRepository

class MassConverterRepositoryImpl(private val preferences: MassPreferences) :
    MassConverterRepository {
    override suspend fun saveMassState(state: MassState) {
        preferences.saveMassState(state)
    }

    override suspend fun getMassState(): MassState {
        return preferences.getMassState()
    }

}