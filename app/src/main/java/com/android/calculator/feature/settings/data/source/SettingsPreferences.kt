package com.android.calculator.feature.settings.data.source

import android.content.Context
import androidx.compose.ui.graphics.toArgb
import com.android.calculator.feature.settings.domain.model.SettingsState
import com.android.calculator.ui.theme.ColorRed

class SettingsPreferences(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("discount_prefs", Context.MODE_PRIVATE)

    fun saveSettingsState(state: SettingsState) {
        with(sharedPreferences.edit()) {
            putBoolean("isButtonRounded", state.isButtonRounded)
            putBoolean("isHapticFeedbackOn", state.isHapticFeedbackOn)
            putBoolean("isDoubleZeroEnabled", state.isDoubleZeroEnabled)
            putInt("themeColor", state.themeColor)
            apply()
        }
    }

    fun getSettingsState(): SettingsState {
        return SettingsState(
            isButtonRounded = sharedPreferences.getBoolean("isButtonRounded", false),
            isHapticFeedbackOn = sharedPreferences.getBoolean("isHapticFeedbackOn", false),
            isDoubleZeroEnabled = sharedPreferences.getBoolean("isDoubleZeroEnabled", true),
            themeColor = sharedPreferences.getInt("themeColor", ColorRed.toArgb())
        )
    }
}
