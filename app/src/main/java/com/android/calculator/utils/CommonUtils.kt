package com.android.calculator.utils

import java.math.BigDecimal
import java.math.MathContext
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CommonUtils {
    // Define constants for max and min value
    const val MAX_LIMIT = 1E99
    const val MIN_LIMIT = 1E-99

    const val MAX_LIMIT_FOR_NORMAL_NOTATION = 1E10
    const val MIN_LIMIT_FOR_NORMAL_NOTATION = 1E-10

    private val operators = setOf('+', '-', '*', '/', '%')

    fun isLastCharOperator(expression: String): Boolean {
        return expression.last() in operators
    }

    fun isLastCharDecimal(expression: String): Boolean {
        return expression.last() == '.'
    }

    fun isLastCharNumber(expression: String): Boolean {
        return expression.last() in "0123456789"
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

    fun removeZeroAfterDecimalPoint(number: Double): String {
        return try {
            if (number % 1 == 0.0) {
                number.toInt().toString()
            } else {
                number.toString()
            }
        } catch (e: NumberFormatException) {
            number.toString()
        }
    }

    fun formatDisplayNumber(number: String): String {
        val normal = convertScientificToNormal(number)
        val scientific = convertNormalToScientific(normal)
        return scientific
    }

    fun convertScientificToNormal(number: String): String {
        return try {
            if (number.endsWith(".")) {
                // Keep the trailing decimal point for numbers like "2."
                val bd = BigDecimal(number.dropLast(1), MathContext.DECIMAL64)
                bd.stripTrailingZeros().toPlainString() + "."
            } else {
                val bd = BigDecimal(number, MathContext.DECIMAL64)
                bd.stripTrailingZeros().toPlainString()
            }
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

    fun formatDate(dateInMillis: Long): String {
        val date = Date(dateInMillis)
        val format = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        return format.format(date)
    }

    fun formatValue(value: Double, precision: Int = 5): String {
        val pattern = "0.${"#".repeat(precision)}E0"
        val normalPattern = "#.${"#".repeat(precision)}"

        return when {
            value >= MAX_LIMIT_FOR_NORMAL_NOTATION -> DecimalFormat(pattern).format(value)
            value <= MIN_LIMIT_FOR_NORMAL_NOTATION && value != 0.0 -> DecimalFormat(pattern).format(value)
            else -> DecimalFormat(normalPattern).format(value)
        }
    }

    fun convertScientificToNormal2(number: String, removeTrailingZeros: Boolean = false): String {
        return try {
            val bd = BigDecimal(number, MathContext.DECIMAL64)

            // Convert to plain string
            val plainString = bd.toPlainString()

            if (removeTrailingZeros) {
                // Remove trailing zeros if needed
                val decimalPointIndex = plainString.indexOf('.')
                if (decimalPointIndex != -1) {
                    // Remove trailing zeros after the decimal point
                    var result = plainString
                    result = result.replace(Regex("(?<=\\d)0+$"), "") // Remove trailing zeros
                    result = result.replace(Regex("\\.$"), "") // Remove trailing decimal point if it's the last character
                    result
                } else {
                    plainString
                }
            } else {
                plainString
            }
        } catch (e: NumberFormatException) {
            number // Return the original string if it can't be parsed
        }
    }
}