package com.android.calculator.app

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.android.calculator.state.ScreenType
import com.android.calculator.ui.screen.calculatorScreenComposable
import com.android.calculator.ui.screen.discountScreenComposable
import com.android.calculator.ui.screen.lengthScreenComposable
import com.android.calculator.ui.screen.massScreenComposable
import com.android.calculator.ui.screen.numeralSystemScreenComposable
import com.android.calculator.ui.screen.tipCalculatorScreenComposable
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