package com.android.calculator.feature.calculatormain.presentation.main.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.android.calculator.feature.calculatormain.presentation.main.CalculatorMainState
import com.android.calculator.ui.shared.components.DrawBlinkingVerticalLine

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun CalculationView(
    state: CalculatorMainState,
    onCopyClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val sizePx = with(LocalDensity.current) { 30.dp.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // 0 is original, 1 is expanded
    var multiplier by remember { mutableFloatStateOf(1.5f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SwipeableIndicator(
                swipeableState = swipeableState,
                sizePx = sizePx,
                onCopyClick = onCopyClick,
                onBookmarkClick = onBookmarkClick
            )

            Text(
                text = buildAnnotatedString {
                    state.expression.forEach { char ->
                        val color =
                            if (char.isDigit() || char == '.') MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSecondary
                        withStyle(style = SpanStyle(color = color)) {
                            append(char)
                        }
                    }
                },
                maxLines = 3,
                fontWeight = FontWeight.W400,
                style = LocalTextStyle.current.copy(
                    fontSize = LocalTextStyle.current.fontSize * multiplier
                ),
                onTextLayout = {
                    if (it.hasVisualOverflow) {
                        multiplier *= 0.9f
                    }
                },
                modifier = Modifier
                    .padding(end = 2.dp)
                    .verticalScroll(rememberScrollState())
            )

            DrawBlinkingVerticalLine()
        }
    }
}
