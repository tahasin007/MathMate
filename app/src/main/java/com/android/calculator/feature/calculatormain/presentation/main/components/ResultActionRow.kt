package com.android.calculator.feature.calculatormain.presentation.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ResultActionRow(
    enabled: Boolean,
    onCopyClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 4.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            onClick = onCopyClick,
            enabled = enabled,
            modifier = Modifier
                .size(36.dp)
                .alpha(if (enabled) 1f else 0.65f)
        ) {
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "Copy",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )
        }

        IconButton(
            onClick = onBookmarkClick,
            enabled = enabled,
            modifier = Modifier
                .size(36.dp)
                .alpha(if (enabled) 1f else 0.65f)
        ) {
            Icon(
                imageVector = Icons.Default.Bookmark,
                contentDescription = "Bookmark",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

