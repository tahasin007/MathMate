package com.android.calculator.actions

sealed class TipCalculatorAction : BaseAction {
    data class EnterHeadCount(val count: Int) : TipCalculatorAction()
    data class EnterTipPercent(val percent: Int) : TipCalculatorAction()
}