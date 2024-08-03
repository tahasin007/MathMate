package com.android.calculator.utils

object NumeralSystemConverter {

    fun convert(number: String, inputUnit: String, outputUnit: String): String {
        val decimalValue = when (inputUnit) {
            "Binary" -> number.toLong(radix = 2)
            "Octal" -> number.toLong(radix = 8)
            "Decimal" -> number.toLong(radix = 10)
            "Hexadecimal" -> number.toLong(radix = 16)
            else -> throw IllegalArgumentException("Unsupported input unit")
        }

        return when (outputUnit) {
            "Binary" -> decimalValue.toString(radix = 2)
            "Octal" -> decimalValue.toString(radix = 8)
            "Decimal" -> decimalValue.toString(radix = 10)
            "Hexadecimal" -> decimalValue.toString(radix = 16).uppercase()
            else -> throw IllegalArgumentException("Unsupported output unit")
        }
    }
}
