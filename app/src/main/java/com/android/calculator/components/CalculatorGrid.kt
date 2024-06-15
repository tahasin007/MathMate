package com.android.calculator.components

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
import com.android.calculator.actions.CalculatorAction
import com.android.calculator.operations.CalculatorOperation

@Composable
fun <T : BaseAction> CalculatorGrid(
    onAction: (T) -> Unit,
    buttonSpacing: Dp
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                symbol = "C",
                buttonTextColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(BaseAction.Clear as T)
                }
            )
            CalculatorButton(
                symbol = "Del",
                buttonTextColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Delete as T)
                }
            )
            CalculatorButton(
                symbol = "%",
                buttonTextColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Operation(CalculatorOperation.Mod) as T)
                }
            )
            CalculatorButton(
                symbol = "/",
                buttonTextColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Operation(CalculatorOperation.Divide) as T)
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                symbol = "7",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(BaseAction.Number(7) as T)
                }
            )
            CalculatorButton(
                symbol = "8",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(BaseAction.Number(8) as T)
                }
            )
            CalculatorButton(
                symbol = "9",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(BaseAction.Number(9) as T)
                }
            )
            CalculatorButton(
                symbol = "*",
                buttonTextColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Operation(CalculatorOperation.Multiply) as T)
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                symbol = "4",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(BaseAction.Number(4) as T)
                }
            )
            CalculatorButton(
                symbol = "5",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(BaseAction.Number(5) as T)
                }
            )
            CalculatorButton(
                symbol = "6",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(BaseAction.Number(6) as T)
                }
            )
            CalculatorButton(
                symbol = "-",
                buttonTextColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Operation(CalculatorOperation.Subtract) as T)
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                symbol = "1",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(BaseAction.Number(1) as T)
                }
            )
            CalculatorButton(
                symbol = "2",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(BaseAction.Number(2) as T)
                }
            )
            CalculatorButton(
                symbol = "3",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(BaseAction.Number(3) as T)
                }
            )
            CalculatorButton(
                symbol = "+",
                buttonTextColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Operation(CalculatorOperation.Add) as T)
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                symbol = "0",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(BaseAction.Number(0) as T)
                }
            )
            CalculatorButton(
                symbol = ".",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Decimal as T)
                }
            )
            CalculatorButton(
                symbol = "=",
                buttonColor = MaterialTheme.colorScheme.onSecondary,
                buttonTextColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .aspectRatio(2f)
                    .weight(2f),
                onClick = {
                    onAction(CalculatorAction.Calculate as T)
                }
            )
        }
    }
}