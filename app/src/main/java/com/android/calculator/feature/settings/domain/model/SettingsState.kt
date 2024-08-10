package com.android.calculator.feature.settings.domain.model

import androidx.compose.ui.graphics.toArgb
import com.android.calculator.ui.theme.ColorRed

data class SettingsState(
    val isButtonRounded: Boolean = false,
    val isHapticFeedbackOn: Boolean = false,
    val isDoubleZeroEnabled: Boolean = true,
    val themeColor: Int = ColorRed.toArgb()
)