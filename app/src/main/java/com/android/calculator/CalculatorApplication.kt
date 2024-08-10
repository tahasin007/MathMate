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
import com.android.calculator.feature.discountcalculator.data.repository.DiscountCalculatorRepositoryImpl
import com.android.calculator.feature.discountcalculator.data.source.DiscountCalculatorPreferences
import com.android.calculator.feature.discountcalculator.presentation.DiscountCalculatorViewModel
import com.android.calculator.feature.settings.data.repository.SettingsRepositoryImpl
import com.android.calculator.feature.settings.data.source.SettingsPreferences
import com.android.calculator.feature.settings.presentaiton.SettingsViewModel
import com.android.calculator.feature.tipcalculator.data.repository.TipCalculatorRepositoryImpl
import com.android.calculator.feature.tipcalculator.data.source.TipCalculatorPreferences
import com.android.calculator.feature.tipcalculator.presentation.TipCalculatorViewModel

class CalculatorApplication : Application() {

    lateinit var historyViewModel: HistoryViewModel
    lateinit var calculatorMainViewModel: CalculatorMainViewModel
    lateinit var tipCalculatorViewModel: TipCalculatorViewModel
    lateinit var discountCalculatorViewModel: DiscountCalculatorViewModel
    lateinit var settingsViewModel: SettingsViewModel

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
        val tioCalculatorRepository =
            TipCalculatorRepositoryImpl(TipCalculatorPreferences(applicationContext))
        val discountCalculatorRepository =
            DiscountCalculatorRepositoryImpl(DiscountCalculatorPreferences(applicationContext))
        val settingsRepository =
            SettingsRepositoryImpl(SettingsPreferences(applicationContext))

        historyViewModel = HistoryViewModel(calculationUseCases)
        calculatorMainViewModel = CalculatorMainViewModel(calculationUseCases)
        tipCalculatorViewModel = TipCalculatorViewModel(tioCalculatorRepository)
        discountCalculatorViewModel = DiscountCalculatorViewModel(discountCalculatorRepository)
        settingsViewModel = SettingsViewModel(settingsRepository)
    }
}
