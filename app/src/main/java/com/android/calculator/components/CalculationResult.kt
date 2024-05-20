package com.android.calculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.calculator.ui.theme.PrimaryLight

@Composable
fun CalculationResult(result: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.12f)
            .padding(start = 5.dp, end = 5.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.onSecondary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 10.dp, start = 10.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            var multiplier by remember { mutableFloatStateOf(1.5f) }

            Text(
                text = result,
                maxLines = 2,
                textAlign = TextAlign.End,
                style = LocalTextStyle.current.copy(
                    fontSize = LocalTextStyle.current.fontSize * multiplier
                ),
                onTextLayout = {
                    if (it.hasVisualOverflow) {
                        multiplier *= 1.99f
                    }
                },
                color = PrimaryLight,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}