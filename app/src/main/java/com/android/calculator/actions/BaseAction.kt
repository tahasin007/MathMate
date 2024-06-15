package com.android.calculator.actions

sealed interface BaseAction {
    data class Number(val number: Int) : BaseAction
    data object Clear : BaseAction
}