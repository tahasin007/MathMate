package com.android.calculator.state

import com.android.calculator.actions.BaseAction

data class CalculatorButtonInfo<T : BaseAction>(
    val symbol: String,
    val action: T,
    val isNumeric: Boolean = false,
    val aspectRatio: Float = 1f,
    val weight: Float = 1f
)
