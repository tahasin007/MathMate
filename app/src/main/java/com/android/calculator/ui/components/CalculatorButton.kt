package com.android.calculator.ui.components

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
import androidx.compose.runtime.setValue
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
import com.android.calculator.ui.animation.shakeEffect

@Composable
fun CalculatorButton(
    symbol: String,
    buttonColor: Color,
    buttonTextColor: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val rippleColor = MaterialTheme.colorScheme.onBackground
    val scale by animateFloatAsState(
        targetValue = if (interactionSource.collectIsPressedAsState().value) 0.25f else 1f,
        label = ""
    )
    var shake by remember { mutableStateOf(false) }

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
            .then(if (shake) Modifier.shakeEffect { shake = false } else Modifier)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(color = rippleColor),
                    onClick = {
                        onClick()
                        shake = true
                    }
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
