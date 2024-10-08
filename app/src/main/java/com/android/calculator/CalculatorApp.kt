package com.android.calculator

import android.app.Activity
import android.view.WindowManager
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.calculator.di.AppModule
import com.android.calculator.feature.calculatormain.presentation.bookmark.BookmarkScreen
import com.android.calculator.feature.calculatormain.presentation.history.HistoryScreen
import com.android.calculator.feature.calculatormain.presentation.main.CalculatorMainScreen
import com.android.calculator.feature.currencyconverter.presentation.CurrencyConverterScreen
import com.android.calculator.feature.discountcalculator.presentation.DiscountScreen
import com.android.calculator.feature.lenghtconverter.presentation.LengthScreen
import com.android.calculator.feature.massconverter.presentation.MassConverterScreen
import com.android.calculator.feature.numeralsystem.presentation.NumeralSystemScreen
import com.android.calculator.feature.settings.presentaiton.SettingsScreen
import com.android.calculator.feature.tipcalculator.presentation.TipCalculatorScreen
import com.android.calculator.ui.theme.CalculatorTheme
import com.android.calculator.utils.ScreenType
import dagger.hilt.android.EntryPointAccessors

@Composable
fun CalculatorApp() {
    val navController = rememberNavController()
    var isDarkTheme by remember { mutableStateOf(false) }

    val context = LocalContext.current.applicationContext
    val settingsRepository = remember {
        EntryPointAccessors.fromApplication(
            context,
            AppModule.SettingsRepositoryEntryPoint::class.java
        )
            .settingsRepository()
    }
    val configuration by settingsRepository.settingsStateFlow.collectAsState()
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
            composable(
                route = ScreenType.CalculatorMain.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() }
            ) {
                CalculatorMainScreen(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(all = 10.dp),
                    isDarkTheme = isDarkTheme,
                    onThemeUpdated = { isDarkTheme = isDarkTheme.not() },
                    configuration = configuration
                )
            }

            composable(
                route = ScreenType.Length.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() }
            ) {
                LengthScreen(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(all = 10.dp),
                    configuration = configuration,
                )
            }

            composable(
                route = ScreenType.Mass.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() }
            ) {
                MassConverterScreen(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(all = 10.dp),
                    configuration = configuration
                )
            }

            composable(
                route = ScreenType.Discount.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() }
            ) {
                DiscountScreen(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(all = 10.dp),
                    configuration = configuration
                )
            }

            composable(
                route = ScreenType.NumeralSystem.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() }
            ) {
                NumeralSystemScreen(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(all = 10.dp),
                    configuration = configuration
                )
            }

            composable(
                route = ScreenType.TipCalculator.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() }
            ) {
                TipCalculatorScreen(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(all = 10.dp),
                    configuration = configuration
                )
            }

            composable(
                route = ScreenType.History.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() }
            ) {
                HistoryScreen(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(all = 10.dp)
                )
            }

            composable(
                route = ScreenType.Bookmark.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() }
            ) {
                BookmarkScreen(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(all = 10.dp)
                )
            }

            composable(
                route = ScreenType.Settings.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() }
            ) {
                SettingsScreen(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(all = 10.dp)
                )
            }

            composable(
                route = ScreenType.Currency.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() }
            ) {
                CurrencyConverterScreen(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(all = 10.dp),
                    configuration = configuration
                )
            }
        }
    }
}

private fun enterTransition(): EnterTransition {
    return fadeIn(
        animationSpec = tween(
            250, easing = LinearEasing
        )
    ) + slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(500, easing = EaseIn)
    )
}

private fun exitTransition(): ExitTransition {
    return fadeOut(
        animationSpec = tween(
            250, easing = LinearEasing
        )
    ) + slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(500, easing = EaseOut)
    )
}