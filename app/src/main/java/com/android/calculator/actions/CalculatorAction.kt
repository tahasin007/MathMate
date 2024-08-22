package com.android.calculator.actions

sealed class CalculatorAction : BaseAction {
    data object Parenthesis : CalculatorAction()
    data class ConverterMenuVisibility(val isSheetOpen: Boolean) : CalculatorAction()
    data class SaveCalculationMenuVisibility(val isSheetOpen: Boolean) : CalculatorAction()
    data object SettingsPage : CalculatorAction()
    data object HistoryPage : CalculatorAction()
}