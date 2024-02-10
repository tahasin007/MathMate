package com.android.calculator.utils

import com.android.calculator.operations.CalculatorOperation
import java.util.Stack

object ExpressionEvaluator {

    fun evaluate(expression: String): Double {
        val tokens = tokenize(expression)
        val numbers = Stack<Double>()
        val operators = Stack<CalculatorOperation>()

        for (token in tokens) {
            if (token.isNumeric()) {
                numbers.add(token.toDouble())
            } else if (token.isOperator()) {
                val currentOperator = getOperator(token)
                while (!operators.isEmpty() && hasPrecedence(operators.peek(), currentOperator)) {
                    applyOperation(numbers, operators)
                }
                operators.push(currentOperator)
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

        for ((index, char) in expression.withIndex()) {
            if (char.isDigit() || char == '.') {
                currentToken.append(char)
            } else if (char == '-' && (index == 0 || expression[index - 1] == '(')) {
                // Handle unary negation
                currentToken.append(char)
            } else {
                if (currentToken.isNotEmpty()) {
                    tokens.add(currentToken.toString())
                    currentToken.clear()
                }
                tokens.add(char.toString())
            }
        }

        if (currentToken.isNotEmpty()) {
            tokens.add(currentToken.toString())
        }

        return tokens
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
            else -> throw IllegalArgumentException("Invalid operator: $token")
        }
    }
}
