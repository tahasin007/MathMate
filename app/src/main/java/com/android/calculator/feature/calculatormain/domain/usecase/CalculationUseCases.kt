package com.android.calculator.feature.calculatormain.domain.usecase

data class CalculationUseCases(
    val insertCalculation: InsertCalculationUseCase,
    val getCalculations: GetCalculationsUseCase,
    val deleteAllCalculations: DeleteAllCalculationsUseCase
)