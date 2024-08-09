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
import com.android.calculator.utils.ScreenType
import com.android.calculator.feature.calculator.presentation.calculatorScreenComposable
import com.android.calculator.feature.discount.presentation.discountScreenComposable
import com.android.calculator.feature.lenghtconverter.presentation.lengthScreenComposable
import com.android.calculator.feature.massconverter.presentation.massScreenComposable
import com.android.calculator.feature.numeralsystem.presentation.numeralSystemScreenComposable
import com.android.calculator.feature.tipcalculator.presentation.tipCalculatorScreenComposable
import com.android.calculator.ui.theme.CalculatorTheme

@Composable
fun CalculatorApp() {
    val navController = rememberNavController()
    var isDarkTheme by remember { mutableStateOf(false) }

    CalculatorTheme(darkTheme = isDarkTheme) {
        NavHost(
            navController = navController,
            startDestination = ScreenType.Calculator.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            calculatorScreenComposable(navController, isDarkTheme) {
                isDarkTheme = isDarkTheme.not()
            }
            lengthScreenComposable(navController)
            massScreenComposable(navController)
            discountScreenComposable(navController)
            numeralSystemScreenComposable(navController)
            tipCalculatorScreenComposable(navController)
        }
    }
}