package com.android.calculator.utils

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
}