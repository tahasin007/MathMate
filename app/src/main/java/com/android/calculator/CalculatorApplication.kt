package com.android.calculator

import android.app.Application
import com.android.calculator.feature.calculatormain.data.repository.CalculationRepositoryImpl
import com.android.calculator.feature.calculatormain.data.source.CalculatorDatabase
import com.android.calculator.feature.calculatormain.domain.usecase.CalculationUseCases
import com.android.calculator.feature.calculatormain.domain.usecase.DeleteAllCalculationsUseCase
import com.android.calculator.feature.calculatormain.domain.usecase.DeleteSelectedCalculationsUseCase
import com.android.calculator.feature.calculatormain.domain.usecase.GetCalculationsUseCase
import com.android.calculator.feature.calculatormain.domain.usecase.InsertCalculationUseCase
import com.android.calculator.feature.calculatormain.presentation.history.HistoryViewModel
import com.android.calculator.feature.calculatormain.presentation.main.CalculatorMainViewModel

class CalculatorApplication : Application() {

    lateinit var historyViewModel: HistoryViewModel
    lateinit var calculatorMainViewModel: CalculatorMainViewModel

    override fun onCreate() {
        super.onCreate()

        // Instantiate the database, repository, use cases, and ViewModel
        val calculationRepository =
            CalculationRepositoryImpl(CalculatorDatabase.getDatabase(this).calculationDao())
        val calculationUseCases = CalculationUseCases(
            insertCalculation = InsertCalculationUseCase(calculationRepository),
            getCalculations = GetCalculationsUseCase(calculationRepository),
            deleteAllCalculations = DeleteAllCalculationsUseCase(calculationRepository),
            deleteSelectedCalculations = DeleteSelectedCalculationsUseCase(calculationRepository)
        )

        historyViewModel = HistoryViewModel(calculationUseCases)
        calculatorMainViewModel = CalculatorMainViewModel(calculationUseCases)
    }
}
