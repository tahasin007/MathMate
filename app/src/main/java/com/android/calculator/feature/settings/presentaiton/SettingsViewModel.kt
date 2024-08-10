package com.android.calculator.feature.settings.presentaiton

import androidx.lifecycle.ViewModel
import com.android.calculator.actions.SettingsAction
import com.android.calculator.feature.settings.data.repository.SettingsRepositoryImpl

class SettingsViewModel(private val repository: SettingsRepositoryImpl) : ViewModel() {
    val settingsState = repository.settingsStateFlow

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.ChangeEnableDoubleZero -> changeEnableDoubleZero(action.status)
            is SettingsAction.ChangeHapticFeedback -> changeHapticFeedback(action.status)
            is SettingsAction.ChangeRoundedButton -> changeRoundedButton(action.status)
            is SettingsAction.ChangeThemeColor -> changeThemeColor(action.color)
        }
    }

    private fun changeEnableDoubleZero(status: Boolean) {
        repository.saveSettingsState(
            settingsState.value.copy(isDoubleZeroEnabled = status)
        )
    }

    private fun changeHapticFeedback(status: Boolean) {
        repository.saveSettingsState(
            settingsState.value.copy(isHapticFeedbackOn = status)
        )
    }

    private fun changeRoundedButton(status: Boolean) {
        repository.saveSettingsState(
            settingsState.value.copy(isButtonRounded = status)
        )
    }

    private fun changeThemeColor(color: Int) {
        repository.saveSettingsState(
            settingsState.value.copy(themeColor = color)
        )
    }
}