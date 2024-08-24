package com.android.calculator.feature.calculatormain.presentation.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.calculator.R
import com.android.calculator.actions.CalculatorAction
import com.android.calculator.ui.shared.components.ConverterIconView
import com.android.calculator.utils.ScreenType

@Composable
fun MenuUnitConverter(
    onAction: (CalculatorAction) -> Unit,
    onNavigate: (screen: String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Unit Converter",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
    Spacer(modifier = Modifier.height(25.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ConverterIconView(
            R.drawable.ic_length,
            ScreenType.Length,
            Modifier.weight(1f)
        ) {
            onNavigate.invoke(ScreenType.Length.route)
            onAction(CalculatorAction.ConverterMenuVisibility(false))
        }
        ConverterIconView(R.drawable.ic_mass, ScreenType.Mass, Modifier.weight(1f)) {
            onNavigate.invoke(ScreenType.Mass.route)
            onAction(CalculatorAction.ConverterMenuVisibility(false))
        }
        ConverterIconView(
            R.drawable.ic_discount,
            ScreenType.Discount,
            Modifier.weight(1f)
        ) {
            onNavigate.invoke(ScreenType.Discount.route)
            onAction(CalculatorAction.ConverterMenuVisibility(false))
        }
    }
    Spacer(modifier = Modifier.height(15.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ConverterIconView(
            R.drawable.ic_tip,
            ScreenType.TipCalculator,
            Modifier.weight(1f)
        ) {
            onNavigate.invoke(ScreenType.TipCalculator.route)
            onAction(CalculatorAction.ConverterMenuVisibility(false))
        }
        ConverterIconView(
            R.drawable.ic_binary,
            ScreenType.NumeralSystem,
            Modifier.weight(1f)
        ) {
            onNavigate.invoke(ScreenType.NumeralSystem.route)
            onAction(CalculatorAction.ConverterMenuVisibility(false))
        }
        ConverterIconView(
            R.drawable.ic_currency_convert,
            ScreenType.Currency,
            Modifier.weight(1f)
        ) {
            onNavigate.invoke(ScreenType.Currency.route)
            onAction(CalculatorAction.ConverterMenuVisibility(false))
        }
    }
}