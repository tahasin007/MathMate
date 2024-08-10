package com.android.calculator.feature.numeralsystem.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.android.calculator.feature.settings.domain.model.SettingsState
import com.android.calculator.utils.ScreenType

fun NavGraphBuilder.numeralSystemScreenComposable(
    navController: NavHostController,
    configuration: SettingsState
) {
    composable(
        route = ScreenType.NumeralSystem.route,
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
        NumeralSystemScreen(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(all = 10.dp),
            configuration = configuration
        )
    }
}