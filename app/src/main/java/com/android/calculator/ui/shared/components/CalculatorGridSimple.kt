package com.android.calculator.ui.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.calculator.actions.BaseAction
import com.android.calculator.feature.settings.domain.model.SettingsState
import com.android.calculator.utils.CalculatorButtonInfo

@Composable
fun CalculatorGridSimple(
    buttons: List<List<CalculatorButtonInfo<out BaseAction>>>,
    onAction: (BaseAction) -> Unit,
    buttonSpacing: Dp = 7.5.dp,
    configuration: SettingsState
) {
    val buttonSize = buttons.size
    val buttonCol1 = buttons.take(buttonSize - 1)
    val buttonCol2 = buttons.takeLast(1).flatten()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            // IntrinsicSize.Min derives the Row height from the left column's natural size
            // (square buttons × rows + spacing).
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(buttonSpacing * 2)
    ) {
        Column(
            modifier = Modifier.weight(3f),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            buttonCol1.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    row.forEach { buttonInfo ->
                        CalculatorButton(
                            symbol = buttonInfo.symbol,
                            buttonColor = MaterialTheme.colorScheme.primary,
                            buttonTextColor = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .aspectRatio(buttonInfo.aspectRatio)
                                .weight(buttonInfo.weight),
                            onClick = { onAction(buttonInfo.action) },
                            configuration = configuration
                        )
                    }
                }
            }
        }

        // Right: action buttons (C / Del) — fillMaxHeight() now works because the parent
        // Row height is pinned by IntrinsicSize.Min. weight(1f) divides the column height
        // equally between buttons, giving the same 2:1 appearance.
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            buttonCol2.forEach { buttonInfo ->
                CalculatorButton(
                    symbol = buttonInfo.symbol,
                    buttonColor = MaterialTheme.colorScheme.primary,
                    buttonTextColor = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onClick = { onAction(buttonInfo.action) },
                    configuration = configuration
                )
            }
        }
    }
}
