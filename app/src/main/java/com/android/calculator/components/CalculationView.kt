package com.android.calculator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.calculator.state.CalculatorState

@Composable
fun CalculationView(state: CalculatorState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = state.expression)
            DrawBlinkingVerticalLine()
        }
    }
}