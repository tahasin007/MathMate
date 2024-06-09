package com.android.calculator.actions

sealed class LengthAction: BaseAction {
    data object Clear : LengthAction()
    data object Delete : LengthAction()
}