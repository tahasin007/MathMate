package com.android.calculator.state

sealed class ScreenType(val route: String) {
    data object Calculator : ScreenType("calculator")
    data object Length : ScreenType("length")
    data object Mass : ScreenType("mass")
    data object Discount : ScreenType("discount")
}