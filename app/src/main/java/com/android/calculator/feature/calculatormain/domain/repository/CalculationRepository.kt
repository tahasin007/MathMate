package com.android.calculator.feature.calculatormain.domain.repository

import com.android.calculator.feature.calculatormain.domain.model.Calculation
import kotlinx.coroutines.flow.Flow

interface CalculationRepository {
    suspend fun insertCalculation(calculations: Calculation)

    fun getCalculations(): Flow<List<Calculation>>

    suspend fun deleteAllCalculations()

    suspend fun deleteCalculations(calculations: List<Calculation>)
}