package com.android.calculator.utils

import java.math.BigDecimal
import java.math.MathContext
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CommonUtils {
    // Define constants for max and min value
    private const val MAX_LIMIT = 1E99
    private const val MIN_LIMIT = 1E-99

    private const val MAX_LIMIT_FOR_NORMAL_NOTATION = 1E10
    private const val MIN_LIMIT_FOR_NORMAL_NOTATION = 1E-10

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
            value <= MIN_LIMIT_FOR_NORMAL_NOTATION && value != 0.0 -> DecimalFormat(pattern).format(
                value
            )

            else -> DecimalFormat(normalPattern).format(value)
        }
    }

    private fun convertScientificToNormal2(number: String, removeTrailingZeros: Boolean = false): String {
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
                    result = result.replace(
                        Regex("\\.$"),
                        ""
                    ) // Remove trailing decimal point if it's the last character
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

    fun formatUnitValues(value: String, number: String, precision: Int = 10): String {
        val isLastCharDecimal = value.endsWith('.')
        val normalNotation = convertScientificToNormal2(value)

        val decimalPart = normalNotation.substringAfter('.', "")
        if (decimalPart.length >= precision) {
            return value// Don't add more digits if there are already 10 digits after the decimal
        }

        val newValueString = if (isLastCharDecimal) "$normalNotation.$number"
        else normalNotation + number

        val newBill = newValueString.toDoubleOrNull()

        if (newBill != null && newBill <= MAX_LIMIT && newBill >= MIN_LIMIT) {
            val formattedValue = formatValue(newBill, precision)

            val finalValue = if (newValueString.contains(".") && !newValueString.contains("E")) {
               newValueString // Keep original string with trailing zeros if it contains a decimal point
            } else {
                formattedValue
            }

            return finalValue
        }
        return value
    }

    fun deleteLastChar(value: String, precision: Int = 10): String {
        if (value.isEmpty()) return value

        val normalNotation = convertScientificToNormal(value)

        val newBill = when {
            normalNotation.length == 1 -> "0"
            else -> normalNotation.dropLast(1)
        }

        val isLastCharDecimal = newBill.endsWith('.')
        return formatValue(newBill.toDouble(), precision) + if (isLastCharDecimal) "." else ""
    }

    /**
     * Splits the string by the last operator, formats the value after the operator,
     * and appends it back to the part before the operator.
     */
    fun formatAndCombine(value: String, number: String): String {
        val operatorRegex = Regex("[+\\-*/%]") // Regex to match operators: +, -, *, /, %
        val lastOperatorIndex = operatorRegex.findAll(value).lastOrNull()?.range?.last

        return if (lastOperatorIndex != null) {
            // Split the string at the last operator
            val beforeOperator = value.substring(0, lastOperatorIndex + 1)
            val afterOperator = value.substring(lastOperatorIndex + 1)

            // Apply formatting to the substring after the last operator
            val formattedValue = formatUnitValues(afterOperator, number)

            // Combine the original part before the operator with the formatted value after the operator
            beforeOperator + formattedValue
        } else {
            // If no operator is found, just format the entire value
            formatUnitValues(value, number)
        }
    }

    /**
     * Deletes the last character from the substring after the last operator,
     * and appends it back to the part before the operator.
     */
    fun deleteLastCharFromExpression(value: String): String {
        val operatorRegex = Regex("[+\\-*/%]") // Regex to match operators: +, -, *, /, %
        val lastOperatorIndex = operatorRegex.findAll(value).lastOrNull()?.range?.last

        return if (lastOperatorIndex != null) {
            // Split the string at the last operator
            val beforeOperator = value.substring(0, lastOperatorIndex + 1)
            val afterOperator = value.substring(lastOperatorIndex + 1)

            // Apply deleteLastChar to the substring after the last operator
            val newAfterOperator = deleteLastChar(afterOperator)

            // If the new value after the operator is empty, remove the operator
            if (newAfterOperator.isEmpty()) {
                beforeOperator.dropLast(1)
            }
            // If the new value after the operator is a single character, don't append
            else if (newAfterOperator.length == 1) {
                beforeOperator
            }
            // Combine the original part before the operator with the new value after the operator
            else beforeOperator + newAfterOperator
        } else {
            // If no operator is found, just delete the last character from the entire value
            deleteLastChar(value)
        }
    }
}