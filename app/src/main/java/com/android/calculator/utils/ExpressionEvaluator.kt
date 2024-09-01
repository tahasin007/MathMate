package com.android.calculator.utils

import com.android.calculator.feature.calculatormain.presentation.main.CalculatorOperation
import java.util.Stack

object ExpressionEvaluator {

    fun evaluate(expression: String): Double {
        val tokens = tokenize(expression)
        val numbers = Stack<Double>()
        val operators = Stack<CalculatorOperation>()

        for (token in tokens) {
            when {
                token.isNumeric() -> {
                    numbers.add(token.toDouble())
                }

                token.isOperator() -> {
                    val currentOperator = getOperator(token)
                    while (!operators.isEmpty() && operators.peek() != CalculatorOperation.Parenthesis && hasPrecedence(
                            operators.peek(),
                            currentOperator
                        )
                    ) {
                        applyOperation(numbers, operators)
                    }
                    operators.push(currentOperator)
                }

                token == "(" -> {
                    operators.push(CalculatorOperation.Parenthesis)
                }

                token == ")" -> {
                    while (operators.peek() != CalculatorOperation.Parenthesis) {
                        applyOperation(numbers, operators)
                    }
                    operators.pop()
                }
            }
        }

        while (!operators.isEmpty()) {
            applyOperation(numbers, operators)
        }

        return numbers.pop()
    }

    private fun tokenize(expression: String): List<String> {
        val tokens = mutableListOf<String>()
        val currentToken = StringBuilder()

        var i = 0
        while (i < expression.length) {
            val char = expression[i]

            if (char.isDigit() || char == '.') {
                currentToken.append(char)
            } else if (char == '-' && (i == 0 || expression[i - 1] == '(')) {
                // Handle unary negation
                currentToken.append(char)
            } else if ((char == 'e' || char == 'E') && i < expression.length - 1 && expression[i + 1].isDigitOrSign()) {
                // Handle scientific notation
                currentToken.append(char)
                i++
                currentToken.append(expression[i]) // append the next character (digit or sign)
            } else {
                if (currentToken.isNotEmpty()) {
                    tokens.add(currentToken.toString())
                    currentToken.clear()
                }
                if (char == '(' && tokens.isNotEmpty() && tokens.last().isNumeric()) {
                    tokens.add("*") // Implicit multiplication for cases like "2(3+4)"
                }
                tokens.add(char.toString())
            }
            i++
        }

        if (currentToken.isNotEmpty()) {
            tokens.add(currentToken.toString())
        }

        return tokens
    }

    private fun Char.isDigitOrSign(): Boolean {
        return this.isDigit() || this == '+' || this == '-'
    }

    private fun applyOperation(numbers: Stack<Double>, operators: Stack<CalculatorOperation>) {
        val number2 = numbers.pop()
        val number1 = numbers.pop()

        val result = when (operators.pop()) {
            CalculatorOperation.Add -> number1 + number2
            CalculatorOperation.Subtract -> number1 - number2
            CalculatorOperation.Multiply -> number1 * number2
            CalculatorOperation.Divide -> number1 / number2
            CalculatorOperation.Mod -> number1 % number2
            else -> throw IllegalArgumentException("Invalid operator")
        }
        numbers.push(result)
    }

    private fun hasPrecedence(op1: CalculatorOperation, op2: CalculatorOperation): Boolean {
        val precedenceMap = mapOf(
            CalculatorOperation.Add to 1,
            CalculatorOperation.Subtract to 1,
            CalculatorOperation.Multiply to 2,
            CalculatorOperation.Divide to 2,
            CalculatorOperation.Mod to 2
        )

        return (precedenceMap[op1] ?: 0) >= (precedenceMap[op2] ?: 0)
    }

    private fun String.isNumeric(): Boolean {
        return this.toDoubleOrNull() != null
    }

    private fun String.isOperator(): Boolean {
        return this == "+" || this == "-" || this == "*" || this == "/" || this == "%"
    }

    private fun getOperator(token: String): CalculatorOperation {
        return when (token) {
            "+" -> CalculatorOperation.Add
            "-" -> CalculatorOperation.Subtract
            "*" -> CalculatorOperation.Multiply
            "/" -> CalculatorOperation.Divide
            "%" -> CalculatorOperation.Mod
            "(" -> CalculatorOperation.Parenthesis
            else -> throw IllegalArgumentException("Invalid operator: $token")
        }
    }
}
