package com.android.calculator.feature.massconverter.data.repository

import com.android.calculator.feature.massconverter.data.source.MassPreference
import com.android.calculator.feature.massconverter.domain.model.MassState
import com.android.calculator.feature.massconverter.domain.repository.MassConverterRepository
import javax.inject.Inject

class MassConverterRepositoryImpl @Inject constructor(
    private val preferences: MassPreference
) : MassConverterRepository {
    override suspend fun saveMassState(state: MassState) {
        preferences.saveMassState(state)
    }

    override suspend fun getMassState(): MassState {
        return preferences.getMassState()
    }

}