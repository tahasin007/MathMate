package com.android.calculator.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.android.calculator.R
import com.android.calculator.ui.theme.ButtonRippleColor

@Composable
fun CalculatorButton(
    symbol: String,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    buttonTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val rippleColor = remember { mutableStateOf(ButtonRippleColor) }
    val scale by animateFloatAsState(
        targetValue = if (interactionSource.collectIsPressedAsState().value) 0.9f else 1f,
        label = ""
    )

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = buttonColor
        ),
        modifier = modifier
            .fillMaxSize()
            .scale(scale)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(color = rippleColor.value),
                    onClick = onClick
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (symbol == "Del") {
                Image(
                    painter = painterResource(id = R.drawable.cal_delete),
                    contentDescription = "Delete",
                    colorFilter = ColorFilter.tint(buttonTextColor)
                )
            } else if (!symbol.isDigitsOnly()) {
                Text(
                    text = symbol,
                    fontSize = 40.sp,
                    color = buttonTextColor
                )
            } else {
                Text(
                    text = symbol,
                    fontSize = 24.sp,
                    color = buttonTextColor
                )
            }
        }
    }
}
