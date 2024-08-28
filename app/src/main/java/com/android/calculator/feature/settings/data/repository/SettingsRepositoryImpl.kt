package com.android.calculator.feature.settings.data.repository

import com.android.calculator.feature.settings.data.source.SettingsPreferences
import com.android.calculator.feature.settings.domain.model.SettingsState
import com.android.calculator.feature.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(private val preferences: SettingsPreferences) : SettingsRepository {

    private val _settingsStateFlow = MutableStateFlow(preferences.getSettingsState())
    override val settingsStateFlow: StateFlow<SettingsState> = _settingsStateFlow

    override suspend fun saveSettingsState(state: SettingsState) {
        preferences.saveSettingsState(state)
        _settingsStateFlow.value = state
    }

    override suspend fun getSettingsState(): SettingsState {
        return preferences.getSettingsState()
    }
}