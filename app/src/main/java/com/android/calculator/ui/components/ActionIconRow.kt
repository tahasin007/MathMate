package com.android.calculator.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.android.calculator.R
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.CalculatorAction
import com.android.calculator.state.CalculatorState

@Composable
fun ActionIconRow(
    state: CalculatorState,
    onAction: (BaseAction) -> Unit,
    isDarkTheme: Boolean = false,
    onThemeUpdated: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.weight(1f),
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = "Delete",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_scale),
            contentDescription = "Delete",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary),
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onAction(CalculatorAction.BottomSheetVisibility(state.isBottomSheetOpen.not()))
                }
        )
        ThemeSwitch(isDarkTheme = isDarkTheme) {
            onThemeUpdated?.invoke()
        }
        Image(
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = "Delete",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary),
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onAction(BaseAction.Delete)
                }
        )
    }
}