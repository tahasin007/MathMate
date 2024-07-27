package com.android.calculator.utils

import java.math.BigDecimal
import java.math.MathContext
import java.text.DecimalFormat

object CommonUtils {
    private val operators = setOf('+', '-', '*', '/', '%')

    fun isLastCharOperator(expression: String): Boolean {
        return expression.last() in operators
    }

    fun isLastCharDecimal(expression: String): Boolean {
        return expression.last() == '.'
    }

    fun canEnterDecimal(expression: String): Boolean {
        if (isLastCharOperator(expression)) return true

        if (expression.isBlank() || expression.last() == '.') {
            return false
        }

        // Check if the current number already contains a decimal point
        val lastNumberIndex =
            expression.lastIndexOfAny(charArrayOf('+', '-', '*', '/', '%'))
        val lastNumber = expression.substring(lastNumberIndex + 1)

        return !lastNumber.contains('.')
    }

    fun removeZeroAfterDecimalPoint(number: String): String {
        return try {
            val doubleValue = number.toDouble()
            if (doubleValue % 1 == 0.0) {
                doubleValue.toInt().toString()
            } else {
                number
            }
        } catch (e: NumberFormatException) {
            number
        }
    }

    fun formatDisplayNumber(number: String): String {
        val normal = convertScientificToNormal(number)
        val scientific = convertNormalToScientific(normal)
        return scientific
    }

    fun convertScientificToNormal(number: String): String {
        return try {
            val bd = BigDecimal(number, MathContext.DECIMAL64)
            bd.stripTrailingZeros().toPlainString()
        } catch (e: NumberFormatException) {
            number // return the original string if it can't be parsed
        }
    }

    fun convertNormalToScientific(number: String): String {
        // Check if the number is valid
        val num = number.toDoubleOrNull() ?: return number

        // Determine if the number should be converted to scientific notation
        return if (number.length >= 10 || (number.split('.')
                .getOrNull(1)?.length ?: 0) >= 10
        ) {
            val decimalFormat = DecimalFormat("0.############E0")
            decimalFormat.format(num)
        } else {
            number
        }
    }
}