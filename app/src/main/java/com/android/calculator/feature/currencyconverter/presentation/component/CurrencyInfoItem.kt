package com.android.calculator.feature.currencyconverter.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.calculator.feature.currencyconverter.presentation.utils.CurrencyUtils
import com.android.calculator.ui.shared.components.DrawBlinkingVerticalLine

@Composable
fun CurrencyInfoItem(
    items: List<String> = CurrencyUtils.defaultCurrencyRate.conversion_rates.keys.toList()
        .sorted(),
    selectedCurrency: String,
    currencyValue: String,
    isCurrentView: Boolean,
    exchangeRate: String,
    onClick: () -> Unit,
    onSelectedUnitChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var multiplier by remember { mutableFloatStateOf(1.0f) }

    val scrollState = rememberScrollState()
    // Scroll to the end of the text if it's longer than the view
    LaunchedEffect(currencyValue) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Column(
        modifier = Modifier.clickable(
            onClick = onClick,
            indication = rememberRipple(
                color = MaterialTheme.colorScheme.primary,
                bounded = true,
                radius = 32.dp
            ),
            interactionSource = remember { MutableInteractionSource() }
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CurrencyInfoHeader(
            selectedCurrency = selectedCurrency,
            items = items,
            expanded = expanded,
            onExpandChange = { expanded = it },
            onSelectedUnitChanged = onSelectedUnitChanged
        )

        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = exchangeRate,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currencyValue,
                maxLines = 1,
                fontSize = 30.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.W300,
                modifier = Modifier.wrapContentWidth(),
                style = LocalTextStyle.current.copy(
                    fontSize = LocalTextStyle.current.fontSize * multiplier
                ),
                onTextLayout = {
                    if (it.hasVisualOverflow) {
                        multiplier *= 1.99f
                    }
                },
                color = MaterialTheme.colorScheme.onPrimary
            )
            if (isCurrentView) {
                Spacer(modifier = Modifier.width(1.dp))
                DrawBlinkingVerticalLine(
                    color = MaterialTheme.colorScheme.onSecondary,
                    lineHeight = 30.dp
                )
            }
        }
    }
}