package com.android.calculator.feature.calculatormain.presentation.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.android.calculator.feature.calculatormain.presentation.main.CalculatorMainState
import com.android.calculator.ui.shared.components.DrawBlinkingVerticalLine

@Composable
fun CalculationView(
    state: CalculatorMainState,
    hasCalculated: Boolean,
    onCopyClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    var multiplier by remember { mutableFloatStateOf(1.5f) }
    val displayExpression = state.expression.ifBlank { "0" }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ResultActionRow(
                enabled = hasCalculated,
                onCopyClick = onCopyClick,
                onBookmarkClick = onBookmarkClick,
                modifier = Modifier.padding(start = 2.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    textAlign = TextAlign.End,
                    text = buildAnnotatedString {
                        displayExpression.forEach { char ->
                            val color =
                                if (char.isDigit() || char == '.') MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.onSecondary
                            withStyle(style = SpanStyle(color = color)) {
                               append(char)
                            }
                        }
                    },
                    maxLines = 3,
                    fontWeight = FontWeight.W400,
                    style = LocalTextStyle.current.copy(
                        fontSize = LocalTextStyle.current.fontSize * multiplier
                    ),
                    onTextLayout = {
                        if (it.hasVisualOverflow) {
                            multiplier *= 0.9f
                        }
                    },
                     modifier = Modifier
                        .padding(end = 2.dp)
                        .verticalScroll(rememberScrollState())
                )

                DrawBlinkingVerticalLine()
            }
        }
    }
}
