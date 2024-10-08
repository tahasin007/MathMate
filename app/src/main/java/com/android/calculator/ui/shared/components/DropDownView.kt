package com.android.calculator.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DropDownView(
    items: Set<String>,
    menuWidth: Dp = 125.dp,
    selectedUnit: String,
    textColor: Color = MaterialTheme.colorScheme.primary,
    onSelectedUnitChanged: (String) -> Unit,
    symbol: String? = null
) {
    var selectedItem by remember { mutableStateOf(selectedUnit) }
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                expanded = !expanded
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = selectedItem,
            fontSize = 18.sp,
            color = textColor
        )

        if (symbol != null) {
            Text(
                text = symbol,
                fontSize = 14.sp,
                color = textColor.copy(alpha = .5f)
            )
        }

        Icon(
            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = "Dropdown Arrow",
            tint = textColor
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .width(menuWidth)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = item,
                            color = if (item == selectedItem) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.weight(1f)
                        )
                        if (item == selectedItem) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                },
                onClick = {
                    selectedItem = item
                    expanded = false
                    onSelectedUnitChanged.invoke(item)
                },
                modifier = Modifier
                    .background(
                        if (item == selectedItem) MaterialTheme.colorScheme.onSecondary.copy(alpha = .1f)
                        else MaterialTheme.colorScheme.primary
                    )
            )
        }
    }
}