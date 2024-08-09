package com.android.calculator.actions

sealed class DiscountAction : BaseAction {
    data class EnterDiscountPercent(val percent: Int) : DiscountAction()
}