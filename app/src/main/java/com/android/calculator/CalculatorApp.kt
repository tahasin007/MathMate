package com.android.calculator

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.calculator.state.ScreenType
import com.android.calculator.ui.screen.CalculatorScreen
import com.android.calculator.ui.screen.DiscountScreen
import com.android.calculator.ui.screen.LengthScreen
import com.android.calculator.ui.screen.MassScreen

@Composable
fun CalculatorApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenType.Calculator.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = ScreenType.Calculator.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        400, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(400, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        400, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(400, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            CalculatorScreen(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(bottom = 20.dp, start = 15.dp, end = 15.dp)
            )
        }

        composable(
            route = ScreenType.Length.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        400, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(400, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        400, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(400, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            LengthScreen(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(bottom = 20.dp, start = 15.dp, end = 15.dp)
            )
        }

        composable(
            route = ScreenType.Mass.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        400, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(400, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        400, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(400, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            MassScreen(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(bottom = 20.dp, start = 15.dp, end = 15.dp)
            )
        }

        composable(
            route = ScreenType.Discount.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        400, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(400, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        400, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(400, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            DiscountScreen(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(bottom = 20.dp, start = 15.dp, end = 15.dp)
            )
        }
    }
}