package com.android.calculator.utils

sealed class NumeralSystem {
    data object Binary : NumeralSystem()
    data object Octal : NumeralSystem()
    data object Decimal : NumeralSystem()
    data object Hexadecimal : NumeralSystem()
}