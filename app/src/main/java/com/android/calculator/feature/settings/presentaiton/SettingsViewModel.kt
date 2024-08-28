package com.android.calculator.feature.settings.presentaiton

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.actions.SettingsAction
import com.android.calculator.feature.settings.data.repository.SettingsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepositoryImpl
) : ViewModel() {

    val settingsState = repository.settingsStateFlow

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.ChangeEnableDoubleZero -> changeEnableDoubleZero(action.status)
            is SettingsAction.ChangeHapticFeedback -> changeHapticFeedback(action.status)
            is SettingsAction.ChangeRoundedButton -> changeRoundedButton(action.status)
            is SettingsAction.ChangeThemeColor -> changeThemeColor(action.color)
            is SettingsAction.ChangeKeepDeviceAwake -> changeKeepDeviceAwake(action.status)
        }
    }

    private fun changeEnableDoubleZero(status: Boolean) {
        viewModelScope.launch {
            repository.saveSettingsState(
                settingsState.value.copy(isDoubleZeroEnabled = status)
            )
        }
    }

    private fun changeHapticFeedback(status: Boolean) {
        viewModelScope.launch {
            repository.saveSettingsState(
                settingsState.value.copy(isHapticFeedbackOn = status)
            )
        }
    }

    private fun changeRoundedButton(status: Boolean) {
        viewModelScope.launch {
            repository.saveSettingsState(
                settingsState.value.copy(isButtonRounded = status)
            )
        }
    }

    private fun changeThemeColor(color: Int) {
        viewModelScope.launch {
            repository.saveSettingsState(
                settingsState.value.copy(themeColor = color)
            )
        }
    }

    private fun changeKeepDeviceAwake(status: Boolean) {
        viewModelScope.launch {
            repository.saveSettingsState(
                settingsState.value.copy(keepDeviceAwake = status)
            )
        }
    }
}