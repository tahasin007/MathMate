package com.android.calculator.feature.calculatormain.domain.usecase

import com.android.calculator.feature.calculatormain.domain.model.Calculation
import com.android.calculator.feature.calculatormain.domain.repository.CalculationRepository

class InsertCalculationUseCase(private val repository: CalculationRepository) {
    suspend operator fun invoke(calculation: Calculation) {
        repository.insertCalculation(calculation)
    }
}