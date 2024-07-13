package com.android.calculator.state

data class DiscountState(
    val inputValue: String = "0",
    val discountValue: String = "0",
    val finalValue: String = "0",
    val savedValue: String = "0",
    val currentView: DiscountView = DiscountView.INPUT
)

enum class DiscountView {
    INPUT,
    DISCOUNT
}