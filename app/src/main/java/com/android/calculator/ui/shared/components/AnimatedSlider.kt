package com.android.calculator.ui.shared.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AnimatedSlider(
    modifier: Modifier = Modifier,
    label: String,
    value: Int,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..100f, // Range of slider values
    thumbRadius: Dp = 16.dp,
    trackHeight: Dp = 4.dp,
    trackColor: Color = MaterialTheme.colorScheme.onSecondary,
    thumbColor: Color = MaterialTheme.colorScheme.onSecondary.copy(alpha = .1f),
    progressBoxColor: Color = MaterialTheme.colorScheme.primary
) {
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current

    var sliderPosition by remember { mutableFloatStateOf(value.toFloat()) }
    var sliderWidth by remember { mutableFloatStateOf(0f) }

    val animatedPosition by animateFloatAsState(
        targetValue = sliderPosition,
        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing), label = ""
    )

    Box(
        modifier = modifier
            .height(thumbRadius * 3) // Increase height to accommodate the progress bar
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val newValue =
                        (change.position.x / sliderWidth) * (valueRange.endInclusive - valueRange.start) + valueRange.start
                    coroutineScope.launch {
                        onValueChange(newValue.coerceIn(valueRange.start, valueRange.endInclusive))
                    }
                }
            }
            .onSizeChanged { size ->
                sliderWidth = size.width.toFloat()
            }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = thumbRadius)
        ) {
            val centerY = size.height / 2 + thumbRadius.toPx() / 2
            val start = Offset(0f, centerY)
            val end = Offset(size.width, centerY)

            // Draw the track
            drawLine(
                color = trackColor,
                start = start,
                end = end,
                strokeWidth = trackHeight.toPx()
            )

            // Draw the animated thumb
            val thumbCenter = Offset(animatedPosition * size.width, centerY)
            drawCircle(
                color = thumbColor,
                radius = thumbRadius.toPx(),
                center = thumbCenter
            )
        }

        // Progress box that follows the thumb
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = (animatedPosition * sliderWidth - with(density) { thumbRadius.toPx() + 88 }).toInt(),
                        y = (-with(density) { thumbRadius.toPx() * 2.25f }).toInt()
                    )
                }
                .background(progressBoxColor),
            contentAlignment = Alignment.Center
        ) {
            val progress =
                (sliderPosition * (valueRange.endInclusive - valueRange.start) + valueRange.start).toInt()
            SliderInfoBox("$label $progress%")
        }
    }

    LaunchedEffect(value) {
        sliderPosition = (value - valueRange.start) / (valueRange.endInclusive - valueRange.start)
    }
}
