package com.android.calculator.state

sealed class ScreenType(val route: String, val screen: String) {
    data object Calculator : ScreenType("calculator", "Calculator")
    data object Length : ScreenType("length", "Length")
    data object Mass : ScreenType("mass", "Mass")
    data object Discount : ScreenType("discount", "Discount")
}