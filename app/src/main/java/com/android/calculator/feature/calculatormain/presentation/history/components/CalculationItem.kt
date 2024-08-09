package com.android.calculator.feature.calculatormain.presentation.history.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.calculator.feature.calculatormain.domain.model.Calculation

@Composable
fun CalculationItem(date: String, calculations: List<Calculation>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = date,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        calculations.forEachIndexed { index, calculation ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        end = 5.dp,
                        bottom = if (index != calculations.lastIndex) 10.dp else 0.dp
                    ),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = buildAnnotatedString {
                        calculation.expression.forEach { char ->
                            val color =
                                if (char.isDigit() || char == '.') MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.onSecondary
                            withStyle(style = SpanStyle(color = color)) {
                                append(char)
                            }
                        }
                    },
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "= ${calculation.result}",
                    fontSize = 16.sp,
                    color = if (calculation.result.startsWith("=")) MaterialTheme.colorScheme.onSecondary
                    else MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}