package com.android.calculator.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleUnitView(
    label: String,
    value: String,
    onClick: (() -> Unit)?,
    isCurrentView: Boolean
) {
    val scrollState = rememberScrollState()

    val backgroundColor =
        if (isCurrentView) MaterialTheme.colorScheme.onSecondary.copy(alpha = .1f)
        else MaterialTheme.colorScheme.primary

    val textColor =
        if (isCurrentView) MaterialTheme.colorScheme.onSecondary
        else MaterialTheme.colorScheme.onPrimary

    // Scroll to the end of the text if it's longer than the view
    LaunchedEffect(value) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(all = 5.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                onClick?.invoke()
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, start = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            var multiplier by remember { mutableFloatStateOf(1.0f) }

            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = label,
                fontSize = 20.sp,
                color = textColor,
                onTextLayout = {
                    if (it.hasVisualOverflow) {
                        multiplier *= 1.99f
                    }
                },
            )

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState) // Enable horizontal scrolling
            ) {
                Text(
                    text = value,
                    maxLines = 1,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(end = 2.dp, top = 2.dp)
                        .wrapContentWidth(), // Ensures the Text takes up only as much space as needed
                    style = LocalTextStyle.current.copy(
                        fontSize = LocalTextStyle.current.fontSize * multiplier
                    ),
                    onTextLayout = {
                        if (it.hasVisualOverflow) {
                            multiplier *= 1.99f
                        }
                    },
                    color = textColor
                )
                if (isCurrentView) {
                    DrawBlinkingVerticalLine(
                        color = MaterialTheme.colorScheme.onSecondary,
                        lineHeight = 20.dp
                    )
                }
            }
        }
    }
}