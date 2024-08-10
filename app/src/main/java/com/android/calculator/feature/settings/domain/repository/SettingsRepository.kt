package com.android.calculator.feature.settings.domain.repository

import com.android.calculator.feature.settings.domain.model.SettingsState
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {
    fun saveSettingsState(state: SettingsState)
    fun getSettingsState(): SettingsState
    val settingsStateFlow: StateFlow<SettingsState>
}