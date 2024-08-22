package com.android.calculator.feature.calculatormain.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.SwipeableState
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SwipeableIndicator(
    swipeableState: SwipeableState<Int>,
    sizePx: Float,
    modifier: Modifier = Modifier,
    onCopyClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Box(
        modifier = modifier
            .width(5.dp + swipeableState.offset.value.roundToInt().dp)
            .height(50.dp)
            .clip(RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp))
            .background(MaterialTheme.colorScheme.onSecondary)
    ) {
        // Gradually reveal buttons as the swipe progresses
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IconButton(
                onClick = onCopyClick,
                modifier = Modifier
                    .size(30.dp)
                    .alpha(swipeableState.offset.value / sizePx),
                enabled = swipeableState.currentValue == 1
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(1.dp)
                )
            }
            IconButton(
                onClick = onBookmarkClick,
                modifier = Modifier
                    .size(30.dp)
                    .alpha(swipeableState.offset.value / sizePx),
                enabled = swipeableState.currentValue == 1
            ) {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = "Bookmark",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(1.dp)
                )
            }
        }
    }
}