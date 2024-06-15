package com.android.calculator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.android.calculator.actions.BaseAction
import com.android.calculator.state.CalculatorButtonInfo

@Composable
fun CalculatorGrid(
    buttons: List<List<CalculatorButtonInfo<out BaseAction>>>,
    onAction: (BaseAction) -> Unit,
    buttonSpacing: Dp
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                row.forEach { buttonInfo ->
                    val buttonColor =
                        if (buttonInfo.symbol == "=") MaterialTheme.colorScheme.onSecondary
                        else MaterialTheme.colorScheme.primary
                    val buttonTextColor =
                        if (buttonInfo.symbol == "=") MaterialTheme.colorScheme.primary
                        else if (buttonInfo.symbol.all { it.isDigit() || it == '.' }) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSecondary

                    CalculatorButton(
                        symbol = buttonInfo.symbol,
                        buttonColor = buttonColor,
                        buttonTextColor = buttonTextColor,
                        modifier = Modifier
                            .aspectRatio(buttonInfo.aspectRatio)
                            .weight(buttonInfo.weight),
                        onClick = {
                            onAction(buttonInfo.action)
                        }
                    )
                }
            }
        }
    }
}