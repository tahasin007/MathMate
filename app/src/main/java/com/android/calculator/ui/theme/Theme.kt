package com.android.calculator.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun CalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    themeColor: Int = ColorRed.toArgb(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme.not()) {
        lightColorScheme(
            primary = PrimaryLight,
            onPrimary = OnPrimaryLight,
            onSecondary = Color(themeColor),
            onBackground = RippleColorLight
        )
    } else {
        darkColorScheme(
            primary = PrimaryDark,
            onPrimary = OnPrimaryDark,
            onSecondary = Color(themeColor),
            onBackground = RippleColorDark
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}