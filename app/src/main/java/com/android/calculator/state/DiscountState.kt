package com.android.calculator.state

data class DiscountState(
    val price: String = "0",
    val discountPercent: Int = 0,
    val finalPrice: String = "0",
    val saved: String = "0"
)