package com.android.calculator.feature.discountcalculator.domain.model

data class DiscountCalculatorState(
    val price: String = "0",
    val discountPercent: Int = 0,
    val finalPrice: String = "0",
    val saved: String = "0"
)