package com.android.calculator.actions

sealed class SettingsAction {
    data class ChangeThemeColor(val color: Int) : SettingsAction()
    data class ChangeRoundedButton(val status: Boolean) : SettingsAction()
    data class ChangeHapticFeedback(val status: Boolean) : SettingsAction()
    data class ChangeEnableDoubleZero(val status: Boolean) : SettingsAction()
}