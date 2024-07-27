package com.android.calculator

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.android.calculator.state.ScreenType
import com.android.calculator.ui.screen.calculatorScreenComposable
import com.android.calculator.ui.screen.discountScreenComposable
import com.android.calculator.ui.screen.lengthScreenComposable
import com.android.calculator.ui.screen.massScreenComposable

@Composable
fun CalculatorApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenType.Calculator.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        calculatorScreenComposable(navController)
        lengthScreenComposable(navController)
        massScreenComposable(navController)
        discountScreenComposable(navController)
    }
}