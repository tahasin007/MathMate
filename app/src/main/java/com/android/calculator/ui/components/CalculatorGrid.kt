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
import androidx.compose.ui.unit.dp
import com.android.calculator.actions.BaseAction
import com.android.calculator.state.CalculatorButtonInfo
import com.android.calculator.utils.NumeralSystem

@Composable
fun CalculatorGrid(
    modifier: Modifier = Modifier,
    buttons: List<List<CalculatorButtonInfo<out BaseAction>>>,
    onAction: (BaseAction) -> Unit,
    buttonSpacing: Dp = 7.5.dp,
    numeralSystem: NumeralSystem = NumeralSystem.Decimal
) {
    Column(
        modifier = modifier,
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
                        else if (buttonInfo.symbol == "." || buttonInfo.isNumeric) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSecondary

                    val regexBinary = "001"
                    val regexOctal = "001234567"
                    val regexDecimal = "00123456789"

                    val isEnabled = buttonInfo.isNumeric.not() || when (numeralSystem) {
                        NumeralSystem.Binary -> {
                            (buttonInfo.isNumeric && buttonInfo.symbol in regexBinary)
                        }

                        NumeralSystem.Octal -> {
                            (buttonInfo.isNumeric && buttonInfo.symbol in regexOctal)
                        }

                        NumeralSystem.Decimal -> {
                            (buttonInfo.isNumeric && buttonInfo.symbol in regexDecimal)
                        }

                        NumeralSystem.Hexadecimal -> true
                    }

                    CalculatorButton(
                        symbol = buttonInfo.symbol,
                        buttonColor = buttonColor,
                        buttonTextColor = buttonTextColor,
                        modifier = Modifier
                            .aspectRatio(buttonInfo.aspectRatio)
                            .weight(buttonInfo.weight),
                        onClick = {
                            onAction(buttonInfo.action)
                        },
                        isEnabled = isEnabled
                    )
                }
            }
        }
    }
}