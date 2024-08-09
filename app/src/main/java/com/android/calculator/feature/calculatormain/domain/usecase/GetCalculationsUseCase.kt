package com.android.calculator.feature.calculatormain.domain.usecase

import com.android.calculator.feature.calculatormain.domain.model.Calculation
import com.android.calculator.feature.calculatormain.domain.repository.CalculationRepository
import kotlinx.coroutines.flow.Flow

class GetCalculationsUseCase(private val repository: CalculationRepository) {
    operator fun invoke(): Flow<List<Calculation>> {
        return repository.getCalculations()
    }
}