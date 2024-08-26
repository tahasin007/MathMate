package com.android.calculator.ui.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

const val BlinkTime = 750L

@Composable
fun DrawBlinkingVerticalLine(
    modifier: Modifier = Modifier,
    lineHeight: Dp = 35.dp,
    color: Color = MaterialTheme.colorScheme.onSecondary,
    key: String = "Cursor"
) {
    var visible by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = key) {
        while (true) {
            visible = false
            delay(BlinkTime)
            visible = true
            delay(BlinkTime)
        }
    }

    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                drawLine(
                    color = color,
                    start = Offset(size.width / 2, -lineHeight.toPx() / 2),
                    end = Offset(size.width / 2, lineHeight.toPx() / 2),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
    }
}