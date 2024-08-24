package com.android.calculator.utils

sealed class ScreenType(val route: String, val screen: String) {
    data object CalculatorMain : ScreenType("calculator_main", "CalculatorMain")
    data object Length : ScreenType("length", "Length")
    data object Mass : ScreenType("mass", "Mass")
    data object Discount : ScreenType("discount", "Discount")
    data object TipCalculator : ScreenType("tip_calculator", "Tip Calculator")
    data object NumeralSystem : ScreenType("numeral_system", "Numeral System")
    data object Currency : ScreenType("currency", "Currency")
    data object Settings : ScreenType("settings", "Settings")
    data object History : ScreenType("history", "History")
    data object Bookmark : ScreenType("bookmark", "Bookmark")
}