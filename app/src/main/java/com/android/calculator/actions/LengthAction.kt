package com.android.calculator.actions

import com.android.calculator.state.LengthView

sealed class LengthAction : BaseAction {
    data class SelectInputUnit(val unit: String) : LengthAction()
    data class SelectOutputUnit(val unit: String) : LengthAction()
    data object ChangeView : LengthAction()
    data object Convert : LengthAction()
}