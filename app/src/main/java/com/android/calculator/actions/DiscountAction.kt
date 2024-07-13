package com.android.calculator.actions

import com.android.calculator.state.DiscountView

sealed class DiscountAction : BaseAction {
    data class ChangeView(val view: DiscountView) : DiscountAction()
}