package com.android.calculator.feature.calculatormain.domain.usecase

import com.android.calculator.feature.calculatormain.domain.usecase.bookmark.InsertBookmarkUseCase
import com.android.calculator.feature.calculatormain.domain.usecase.history.DeleteSelectedCalculationsUseCase
import com.android.calculator.feature.calculatormain.domain.usecase.history.GetCalculationsUseCase
import com.android.calculator.feature.calculatormain.domain.usecase.history.InsertCalculationUseCase

data class CalculationUseCases(
    val insertCalculation: InsertCalculationUseCase,
    val getCalculations: GetCalculationsUseCase,
    val deleteSelectedCalculations: DeleteSelectedCalculationsUseCase,
    val insertBookmark: InsertBookmarkUseCase
)