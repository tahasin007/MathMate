package com.android.calculator.actions

import com.android.calculator.state.MassView

sealed class MassAction : BaseAction {
    data class ChangeInputUnit(val unit: String) : MassAction()
    data class ChangeOutputUnit(val unit: String) : MassAction()
    data class ChangeView(val view: MassView) : MassAction()
    data object Convert : MassAction()
}