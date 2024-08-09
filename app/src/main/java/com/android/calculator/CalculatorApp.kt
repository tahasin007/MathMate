package com.android.calculator

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.android.calculator.feature.calculator.history.presentation.historyScreenComposable
import com.android.calculator.utils.ScreenType
import com.android.calculator.feature.calculator.main.presentation.calculatorMainScreenComposable
import com.android.calculator.feature.discount.presentation.discountScreenComposable
import com.android.calculator.feature.lenghtconverter.presentation.lengthScreenComposable
import com.android.calculator.feature.massconverter.presentation.massScreenComposable
import com.android.calculator.feature.numeralsystem.presentation.numeralSystemScreenComposable
import com.android.calculator.feature.settings.presentaiton.settingsScreenComposable
import com.android.calculator.feature.tipcalculator.presentation.tipCalculatorScreenComposable
import com.android.calculator.ui.theme.CalculatorTheme

@Composable
fun CalculatorApp() {
    val navController = rememberNavController()
    var isDarkTheme by remember { mutableStateOf(false) }

    CalculatorTheme(darkTheme = isDarkTheme) {
        NavHost(
            navController = navController,
            startDestination = ScreenType.CalculatorMain.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            calculatorMainScreenComposable(navController, isDarkTheme) {
                isDarkTheme = isDarkTheme.not()
            }
            lengthScreenComposable(navController)
            massScreenComposable(navController)
            discountScreenComposable(navController)
            numeralSystemScreenComposable(navController)
            tipCalculatorScreenComposable(navController)
            settingsScreenComposable(navController)
            historyScreenComposable(navController)
        }
    }
}