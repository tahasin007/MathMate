package com.android.calculator.ui.common.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun Modifier.shakeEffect(onShakeEnd: () -> Unit): Modifier {
    val offsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            offsetX.animateTo(
                targetValue = 15f, // Shake to the right
                animationSpec = tween(durationMillis = 75, easing = LinearEasing)
            )
            offsetX.animateTo(
                targetValue = -15f, // Shake to the left
                animationSpec = tween(durationMillis = 75, easing = LinearEasing)
            )
            offsetX.animateTo(
                targetValue = 0f, // Return to the original position
                animationSpec = tween(durationMillis = 75, easing = LinearEasing)
            )
            onShakeEnd() // Reset shake state
        }
    }

    return this.offset { IntOffset(offsetX.value.roundToInt(), 0) }
}
