package com.android.calculator.feature.calculatormain.data.repository

import com.android.calculator.feature.calculatormain.data.source.CalculationDao
import com.android.calculator.feature.calculatormain.domain.model.Calculation
import com.android.calculator.feature.calculatormain.domain.repository.CalculationRepository
import kotlinx.coroutines.flow.Flow

class CalculationRepositoryImpl(private val dao: CalculationDao) : CalculationRepository {
    override suspend fun insertCalculation(calculations: Calculation) {
        return dao.insertCalculation(calculations)
    }

    override fun getCalculations(): Flow<List<Calculation>> {
        return dao.getAllCalculations()
    }

    override suspend fun deleteAllCalculations() {
        dao.deleteAllCalculations()
    }

    override suspend fun deleteCalculations(calculations: List<Calculation>) {
        dao.deleteCalculations(calculations.map { it.id })
    }
}