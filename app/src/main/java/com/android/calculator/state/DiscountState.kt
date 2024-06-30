package com.android.calculator.state

data class DiscountState(
    val inputValue: String = "0",
    val outputValue: String = "0",
    val currentView: DiscountView = DiscountView.INPUT
)

enum class DiscountView {
    INPUT,
    OUTPUT
}