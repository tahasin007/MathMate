package com.android.calculator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.calculator.actions.BaseAction
import com.android.calculator.state.CalculatorButtonInfo

@Composable
fun CalculatorGridSimple(
    buttons: List<List<CalculatorButtonInfo<out BaseAction>>>,
    onAction: (BaseAction) -> Unit,
    buttonSpacing: Dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val buttonSize = buttons.size
        val buttonCol1 = buttons.take(buttonSize - 1)
        val buttonCol2 = buttons.takeLast(1).flatten()

        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            buttonCol1.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    row.forEach { buttonInfo ->
                        val buttonColor = MaterialTheme.colorScheme.primary
                        val buttonTextColor = MaterialTheme.colorScheme.onSecondary

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

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            buttonCol2.forEach { buttonInfo ->
                val buttonColor = MaterialTheme.colorScheme.primary
                val buttonTextColor = MaterialTheme.colorScheme.onSecondary

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CalculatorButton(
                        symbol = buttonInfo.symbol,
                        buttonColor = buttonColor,
                        buttonTextColor = buttonTextColor,
                        modifier = Modifier
                            .aspectRatio(buttonInfo.aspectRatio / 2)
                            .weight(buttonInfo.weight / 2),
                        onClick = {
                            onAction(buttonInfo.action)
                        }
                    )
                }
            }
        }
    }
}
