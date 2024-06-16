package com.android.calculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun UnitView(
    value: String,
    items: Set<String>,
    selectedUnit: String,
    onClick: () -> Unit,
    onSelectedUnitChanged: (String) -> Unit,
    isCurrentView: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(all = 5.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.onSecondary)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, start = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {}
            ) {
                DropDownView(
                    selectedUnit = selectedUnit,
                    items = items,
                    onSelectedUnitChanged = onSelectedUnitChanged
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                var multiplier by remember { mutableFloatStateOf(1.0f) }
                Text(
                    text = value,
                    maxLines = 1,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(end = 2.dp),
                    style = LocalTextStyle.current.copy(
                        fontSize = LocalTextStyle.current.fontSize * multiplier
                    ),
                    onTextLayout = {
                        if (it.hasVisualOverflow) {
                            multiplier *= 1.99f
                        }
                    },
                    color = MaterialTheme.colorScheme.primary
                )
                if (isCurrentView) {
                    DrawBlinkingVerticalLine(color = MaterialTheme.colorScheme.onTertiary)
                }
            }
        }
    }
}