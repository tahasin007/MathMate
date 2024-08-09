package com.android.calculator.actions

import com.android.calculator.feature.numeralsystem.presentation.NumeralSystemView

sealed class NumeralSystemAction : BaseAction {
    data class HexSymbol(val symbol: String) : NumeralSystemAction()
    data class ChangeInputUnit(val unit: String) : NumeralSystemAction()
    data class ChangeOutputUnit(val unit: String) : NumeralSystemAction()
    data class ChangeView(val view: NumeralSystemView) : NumeralSystemAction()
}