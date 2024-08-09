package com.android.calculator.feature.calculatormain.domain.usecase

import com.android.calculator.feature.calculatormain.domain.repository.CalculationRepository

class DeleteAllCalculationsUseCase(private val repository: CalculationRepository) {
    suspend operator fun invoke() {
        repository.deleteAllCalculations()
    }
}