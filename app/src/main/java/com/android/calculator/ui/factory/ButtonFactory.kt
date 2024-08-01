package com.android.calculator.ui.factory

import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.CalculatorAction
import com.android.calculator.operations.CalculatorOperation
import com.android.calculator.state.CalculatorButtonInfo
import com.android.calculator.state.ScreenType

class ButtonFactory {
    fun getButtons(screenType: ScreenType): List<List<CalculatorButtonInfo<out BaseAction>>> {
        return when (screenType) {
            ScreenType.Calculator -> getCalculatorButtons()
            ScreenType.Length, ScreenType.Mass -> getBasicButtons()
            ScreenType.Discount -> getDiscountButtons()
            ScreenType.TipCalculator -> TODO()
            ScreenType.NumeralSystem -> TODO()
            ScreenType.Currency -> TODO()
        }
    }

    private fun getCalculatorButtons(): List<List<CalculatorButtonInfo<out BaseAction>>> {

        return listOf(
            listOf(
                CalculatorButtonInfo("C", BaseAction.Clear),
                CalculatorButtonInfo("Parenthesis", CalculatorAction.Parenthesis),
                CalculatorButtonInfo("%", BaseAction.Operation(CalculatorOperation.Mod)),
                CalculatorButtonInfo("/", BaseAction.Operation(CalculatorOperation.Divide))
            ),
            listOf(
                CalculatorButtonInfo("7", BaseAction.Number(7)),
                CalculatorButtonInfo("8", BaseAction.Number(8)),
                CalculatorButtonInfo("9", BaseAction.Number(9)),
                CalculatorButtonInfo("*", BaseAction.Operation(CalculatorOperation.Multiply))
            ),
            listOf(
                CalculatorButtonInfo("4", BaseAction.Number(4)),
                CalculatorButtonInfo("5", BaseAction.Number(5)),
                CalculatorButtonInfo("6", BaseAction.Number(6)),
                CalculatorButtonInfo("-", BaseAction.Operation(CalculatorOperation.Subtract))
            ),
            listOf(
                CalculatorButtonInfo("1", BaseAction.Number(1)),
                CalculatorButtonInfo("2", BaseAction.Number(2)),
                CalculatorButtonInfo("3", BaseAction.Number(3)),
                CalculatorButtonInfo("+", BaseAction.Operation(CalculatorOperation.Add))
            ),
            listOf(
                CalculatorButtonInfo("0", BaseAction.Number(0)),
                CalculatorButtonInfo("00", BaseAction.DoubleZero("00")),
                CalculatorButtonInfo(".", BaseAction.Decimal),
                CalculatorButtonInfo("=", BaseAction.Calculate)
            )
        )
    }

    private fun getBasicButtons(): List<List<CalculatorButtonInfo<out BaseAction>>> {

        return listOf(
            listOf(
                CalculatorButtonInfo("C", BaseAction.Clear),
                CalculatorButtonInfo("Del", BaseAction.Delete),
                CalculatorButtonInfo("%", BaseAction.Operation(CalculatorOperation.Mod)),
                CalculatorButtonInfo("/", BaseAction.Operation(CalculatorOperation.Divide))
            ),
            listOf(
                CalculatorButtonInfo("7", BaseAction.Number(7)),
                CalculatorButtonInfo("8", BaseAction.Number(8)),
                CalculatorButtonInfo("9", BaseAction.Number(9)),
                CalculatorButtonInfo("*", BaseAction.Operation(CalculatorOperation.Multiply))
            ),
            listOf(
                CalculatorButtonInfo("4", BaseAction.Number(4)),
                CalculatorButtonInfo("5", BaseAction.Number(5)),
                CalculatorButtonInfo("6", BaseAction.Number(6)),
                CalculatorButtonInfo("-", BaseAction.Operation(CalculatorOperation.Subtract))
            ),
            listOf(
                CalculatorButtonInfo("1", BaseAction.Number(1)),
                CalculatorButtonInfo("2", BaseAction.Number(2)),
                CalculatorButtonInfo("3", BaseAction.Number(3)),
                CalculatorButtonInfo("+", BaseAction.Operation(CalculatorOperation.Add))
            ),
            listOf(
                CalculatorButtonInfo("0", BaseAction.Number(0)),
                CalculatorButtonInfo(".", BaseAction.Decimal),
                CalculatorButtonInfo("=", BaseAction.Calculate, aspectRatio = 2f, weight = 2f)
            )
        )
    }

    private fun getDiscountButtons(): List<List<CalculatorButtonInfo<out BaseAction>>> {

        return listOf(
            listOf(
                CalculatorButtonInfo("7", BaseAction.Number(7)),
                CalculatorButtonInfo("8", BaseAction.Number(8)),
                CalculatorButtonInfo("9", BaseAction.Number(9))
            ),
            listOf(
                CalculatorButtonInfo("4", BaseAction.Number(4)),
                CalculatorButtonInfo("5", BaseAction.Number(5)),
                CalculatorButtonInfo("6", BaseAction.Number(6))
            ),
            listOf(
                CalculatorButtonInfo("1", BaseAction.Number(1)),
                CalculatorButtonInfo("2", BaseAction.Number(2)),
                CalculatorButtonInfo("3", BaseAction.Number(3))
            ),
            listOf(
                CalculatorButtonInfo("00", BaseAction.DoubleZero("00")),
                CalculatorButtonInfo("0", BaseAction.Number(0)),
                CalculatorButtonInfo(".", BaseAction.Decimal)
            ),
            listOf(
                CalculatorButtonInfo("C", BaseAction.Clear),
                CalculatorButtonInfo("Del", BaseAction.Delete)
            )
        )
    }
}