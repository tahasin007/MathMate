package com.android.calculator.actions

import com.android.calculator.state.LengthView

sealed class LengthAction : BaseAction {
    data class ChangeInputUnit(val unit: String) : LengthAction()
    data class ChangeOutputUnit(val unit: String) : LengthAction()
    data class ChangeView(val view: LengthView) : LengthAction()
    data object Convert : LengthAction()
}