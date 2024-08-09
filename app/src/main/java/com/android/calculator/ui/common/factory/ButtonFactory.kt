package com.android.calculator.ui.common.factory

import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.CalculatorAction
import com.android.calculator.actions.NumeralSystemAction
import com.android.calculator.feature.calculatormain.presentation.main.CalculatorOperation
import com.android.calculator.utils.CalculatorButtonInfo
import com.android.calculator.utils.ScreenType

class ButtonFactory {
    fun getButtons(screenType: ScreenType): List<List<CalculatorButtonInfo<out BaseAction>>> {
        return when (screenType) {
            ScreenType.CalculatorMain -> getCalculatorButtons()
            ScreenType.Length, ScreenType.Mass -> getBasicButtons()
            ScreenType.Discount -> getSimpleGridButtons()
            ScreenType.TipCalculator -> getSimpleGridButtons()
            ScreenType.NumeralSystem -> getNumeralSystemButtons()
            ScreenType.Currency -> getBasicButtons()
            ScreenType.Settings, ScreenType.History -> emptyList()
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
                CalculatorButtonInfo("7", BaseAction.Number(7), true),
                CalculatorButtonInfo("8", BaseAction.Number(8), true),
                CalculatorButtonInfo("9", BaseAction.Number(9), true),
                CalculatorButtonInfo("*", BaseAction.Operation(CalculatorOperation.Multiply))
            ),
            listOf(
                CalculatorButtonInfo("4", BaseAction.Number(4), true),
                CalculatorButtonInfo("5", BaseAction.Number(5), true),
                CalculatorButtonInfo("6", BaseAction.Number(6), true),
                CalculatorButtonInfo("-", BaseAction.Operation(CalculatorOperation.Subtract))
            ),
            listOf(
                CalculatorButtonInfo("1", BaseAction.Number(1), true),
                CalculatorButtonInfo("2", BaseAction.Number(2), true),
                CalculatorButtonInfo("3", BaseAction.Number(3), true),
                CalculatorButtonInfo("+", BaseAction.Operation(CalculatorOperation.Add))
            ),
            listOf(
                CalculatorButtonInfo("0", BaseAction.Number(0), true),
                CalculatorButtonInfo("00", BaseAction.DoubleZero("00"), true),
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
                CalculatorButtonInfo("7", BaseAction.Number(7), true),
                CalculatorButtonInfo("8", BaseAction.Number(8), true),
                CalculatorButtonInfo("9", BaseAction.Number(9), true),
                CalculatorButtonInfo("*", BaseAction.Operation(CalculatorOperation.Multiply))
            ),
            listOf(
                CalculatorButtonInfo("4", BaseAction.Number(4), true),
                CalculatorButtonInfo("5", BaseAction.Number(5), true),
                CalculatorButtonInfo("6", BaseAction.Number(6), true),
                CalculatorButtonInfo("-", BaseAction.Operation(CalculatorOperation.Subtract))
            ),
            listOf(
                CalculatorButtonInfo("1", BaseAction.Number(1), true),
                CalculatorButtonInfo("2", BaseAction.Number(2), true),
                CalculatorButtonInfo("3", BaseAction.Number(3), true),
                CalculatorButtonInfo("+", BaseAction.Operation(CalculatorOperation.Add))
            ),
            listOf(
                CalculatorButtonInfo("0", BaseAction.Number(0), true),
                CalculatorButtonInfo(".", BaseAction.Decimal),
                CalculatorButtonInfo("=", BaseAction.Calculate, aspectRatio = 2f, weight = 2f)
            )
        )
    }

    private fun getSimpleGridButtons(): List<List<CalculatorButtonInfo<out BaseAction>>> {

        return listOf(
            listOf(
                CalculatorButtonInfo("7", BaseAction.Number(7), true),
                CalculatorButtonInfo("8", BaseAction.Number(8), true),
                CalculatorButtonInfo("9", BaseAction.Number(9), true)
            ),
            listOf(
                CalculatorButtonInfo("4", BaseAction.Number(4), true),
                CalculatorButtonInfo("5", BaseAction.Number(5), true),
                CalculatorButtonInfo("6", BaseAction.Number(6), true)
            ),
            listOf(
                CalculatorButtonInfo("1", BaseAction.Number(1), true),
                CalculatorButtonInfo("2", BaseAction.Number(2), true),
                CalculatorButtonInfo("3", BaseAction.Number(3), true)
            ),
            listOf(
                CalculatorButtonInfo("00", BaseAction.DoubleZero("00"), true),
                CalculatorButtonInfo("0", BaseAction.Number(0), true),
                CalculatorButtonInfo(".", BaseAction.Decimal)
            ),
            listOf(
                CalculatorButtonInfo("C", BaseAction.Clear),
                CalculatorButtonInfo("Del", BaseAction.Delete)
            )
        )
    }

    private fun getNumeralSystemButtons(): List<List<CalculatorButtonInfo<out BaseAction>>> {

        return listOf(
            listOf(
                CalculatorButtonInfo("C", BaseAction.Clear),
                CalculatorButtonInfo("Del", BaseAction.Delete),
                CalculatorButtonInfo("F", NumeralSystemAction.HexSymbol("F"), true),
                CalculatorButtonInfo("E", NumeralSystemAction.HexSymbol("E"), true)
            ),
            listOf(
                CalculatorButtonInfo("7", BaseAction.Number(7), true),
                CalculatorButtonInfo("8", BaseAction.Number(8), true),
                CalculatorButtonInfo("9", BaseAction.Number(9), true),
                CalculatorButtonInfo("D", NumeralSystemAction.HexSymbol("D"), true)
            ),
            listOf(
                CalculatorButtonInfo("4", BaseAction.Number(4), true),
                CalculatorButtonInfo("5", BaseAction.Number(5), true),
                CalculatorButtonInfo("6", BaseAction.Number(6), true),
                CalculatorButtonInfo("C", NumeralSystemAction.HexSymbol("C"), true)
            ),
            listOf(
                CalculatorButtonInfo("1", BaseAction.Number(1), true),
                CalculatorButtonInfo("2", BaseAction.Number(2), true),
                CalculatorButtonInfo("3", BaseAction.Number(3), true),
                CalculatorButtonInfo("B", NumeralSystemAction.HexSymbol("B"), true)
            ),
            listOf(
                CalculatorButtonInfo("0", BaseAction.Number(0), true),
                CalculatorButtonInfo("00", BaseAction.DoubleZero("00"), true),
                CalculatorButtonInfo(".", BaseAction.Decimal),
                CalculatorButtonInfo("A", NumeralSystemAction.HexSymbol("A"), true)
            )
        )
    }
}