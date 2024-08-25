package com.android.calculator.actions

import com.android.calculator.feature.currencyconverter.presentation.CurrencyView

sealed class CurrencyAction : BaseAction {
    data class ChangeFromUnit(val unit: String) : CurrencyAction()
    data class ChangeToUnit(val unit: String) : CurrencyAction()
    data class ChangeView(val view: CurrencyView) : CurrencyAction()
    data object SwitchView : CurrencyAction()
    data object Convert : CurrencyAction()
}