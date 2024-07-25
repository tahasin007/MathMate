package com.android.calculator.actions

sealed class CalculatorAction : BaseAction {
    data object Parenthesis : CalculatorAction()
    data object BottomSheetVisibility : CalculatorAction()
    data object SettingsPage : CalculatorAction()
    data object HistoryPage : CalculatorAction()
}