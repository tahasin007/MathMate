package com.android.calculator.feature.numeralsystem.presentation.utils

import java.math.BigDecimal
import java.math.BigInteger

object NumeralSystemConverter {

    fun convert(number: String, inputUnit: String, outputUnit: String): String {
        val decimalValue = when (inputUnit) {
            "Binary" -> number.toBigDecimalWithRadix(2)
            "Octal" -> number.toBigDecimalWithRadix(8)
            "Decimal" -> number.toBigDecimalOrNull() ?: throw NumberFormatException("Invalid Decimal number")
            "Hexadecimal" -> number.toBigDecimalWithRadix(16)
            else -> throw IllegalArgumentException("Unsupported input unit")
        }

        return when (outputUnit) {
            "Binary" -> decimalValue.toStringWithRadix(2)
            "Octal" -> decimalValue.toStringWithRadix(8)
            "Decimal" -> decimalValue.stripTrailingZeros().toPlainString()
            "Hexadecimal" -> decimalValue.toStringWithRadix(16).uppercase()
            else -> throw IllegalArgumentException("Unsupported output unit")
        }
    }

    private fun String.toBigDecimalWithRadix(radix: Int): BigDecimal {
        val parts = this.split(".")
        val integerPart = BigInteger(parts[0], radix)
        return if (parts.size == 2) {
            val fractionalPart = BigDecimal(BigInteger(parts[1], radix))
                .divide(BigDecimal(radix).pow(parts[1].length))
            BigDecimal(integerPart) + fractionalPart
        } else {
            BigDecimal(integerPart)
        }
    }

    private fun BigDecimal.toStringWithRadix(radix: Int): String {
        val integerPart = this.toBigInteger()
        val fractionalPart = this.subtract(BigDecimal(integerPart))

        val integerString = integerPart.toString(radix)
        if (fractionalPart == BigDecimal.ZERO) {
            return integerString
        }

        val fractionalString = buildString {
            var frac = fractionalPart
            var iterationCount = 0
            while (frac > BigDecimal.ZERO && iterationCount < 10) { // Limiting to 10 digits for fractional precision
                frac = frac.multiply(BigDecimal(radix))
                val digit = frac.toBigInteger()
                append(digit.toString(radix))
                frac = frac.subtract(BigDecimal(digit))
                iterationCount++
            }
        }

        return "$integerString.$fractionalString".trimEnd('0')
    }
}
