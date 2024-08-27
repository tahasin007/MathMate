package com.android.calculator.feature.lenghtconverter.domain.model

data class LengthState(
    val inputUnit: String = "Meter",
    val outputUnit: String = "Kilometer",
    val inputValue: String = "0",
    val outputValue: String = "0",
    val currentView: LengthView = LengthView.INPUT
)