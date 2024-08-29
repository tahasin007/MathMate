package com.android.calculator.feature.calculatormain.domain.usecase.history

import com.android.calculator.feature.calculatormain.domain.model.Calculation
import com.android.calculator.feature.calculatormain.domain.repository.CalculationRepository

class DeleteSelectedCalculationsUseCase(private val repository: CalculationRepository) {
    suspend operator fun invoke(calculations: List<Calculation>) {
        repository.deleteCalculations(calculations)
    }
}