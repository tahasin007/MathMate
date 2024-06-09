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
fun CalculatorGrid(
    onAction: (BaseAction) -> Unit,
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
                    onAction(CalculatorAction.Clear)
                }
            )
            CalculatorButton(
                symbol = "Del",
                buttonTextColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Delete)
                }
            )
            CalculatorButton(
                symbol = "%",
                buttonTextColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Operation(CalculatorOperation.Mod))
                }
            )
            CalculatorButton(
                symbol = "/",
                buttonTextColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Operation(CalculatorOperation.Divide))
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
                    onAction(CalculatorAction.Number(7))
                }
            )
            CalculatorButton(
                symbol = "8",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Number(8))
                }
            )
            CalculatorButton(
                symbol = "9",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Number(9))
                }
            )
            CalculatorButton(
                symbol = "*",
                buttonTextColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Operation(CalculatorOperation.Multiply))
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
                    onAction(CalculatorAction.Number(4))
                }
            )
            CalculatorButton(
                symbol = "5",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Number(5))
                }
            )
            CalculatorButton(
                symbol = "6",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Number(6))
                }
            )
            CalculatorButton(
                symbol = "-",
                buttonTextColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Operation(CalculatorOperation.Subtract))
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
                    onAction(CalculatorAction.Number(1))
                }
            )
            CalculatorButton(
                symbol = "2",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Number(2))
                }
            )
            CalculatorButton(
                symbol = "3",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Number(3))
                }
            )
            CalculatorButton(
                symbol = "+",
                buttonTextColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Operation(CalculatorOperation.Add))
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
                    onAction(CalculatorAction.Number(0))
                }
            )
            CalculatorButton(
                symbol = ".",
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f),
                onClick = {
                    onAction(CalculatorAction.Decimal)
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
                    onAction(CalculatorAction.Calculate)
                }
            )
        }
    }
}