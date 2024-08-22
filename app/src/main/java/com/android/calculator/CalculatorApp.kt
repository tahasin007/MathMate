package com.android.calculator

import android.app.Activity
import android.view.WindowManager
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.android.calculator.feature.calculatormain.presentation.history.historyScreenComposable
import com.android.calculator.feature.calculatormain.presentation.main.calculatorMainScreenComposable
import com.android.calculator.feature.discountcalculator.presentation.discountScreenComposable
import com.android.calculator.feature.lenghtconverter.presentation.lengthScreenComposable
import com.android.calculator.feature.massconverter.presentation.massScreenComposable
import com.android.calculator.feature.numeralsystem.presentation.numeralSystemScreenComposable
import com.android.calculator.feature.settings.presentaiton.settingsScreenComposable
import com.android.calculator.feature.tipcalculator.presentation.tipCalculatorScreenComposable
import com.android.calculator.ui.theme.CalculatorTheme
import com.android.calculator.utils.ScreenType

@Composable
fun CalculatorApp(app: CalculatorApplication) {
    val navController = rememberNavController()
    var isDarkTheme by remember { mutableStateOf(false) }
    val configuration by app.settingsViewModel.settingsState.collectAsState()
    val themeColor = configuration.themeColor

    val activity = LocalContext.current as Activity

    // Keep the device awake based on configuration
    DisposableEffect(configuration.keepDeviceAwake) {
        if (configuration.keepDeviceAwake) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        onDispose {
            if (configuration.keepDeviceAwake) {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }
    }

    CalculatorTheme(darkTheme = isDarkTheme, themeColor = themeColor) {
        NavHost(
            navController = navController,
            startDestination = ScreenType.CalculatorMain.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            calculatorMainScreenComposable(app, navController, isDarkTheme, configuration) {
                isDarkTheme = isDarkTheme.not()
            }
            lengthScreenComposable(navController, configuration)
            massScreenComposable(navController, configuration)
            discountScreenComposable(app, navController, configuration)
            numeralSystemScreenComposable(navController, configuration)
            tipCalculatorScreenComposable(app, navController, configuration)
            historyScreenComposable(app, navController, configuration)
            settingsScreenComposable(app, navController)
        }
    }
}