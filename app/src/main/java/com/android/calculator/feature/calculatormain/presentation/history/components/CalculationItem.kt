package com.android.calculator.feature.calculatormain.presentation.history.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.calculator.feature.calculatormain.domain.model.Calculation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalculationItem(
    date: String,
    calculations: List<Calculation>,
    selectionMode: Boolean,
    selectedItems: MutableList<Calculation>,
    onLongPress: () -> Unit
) {
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {
                            if (selectionMode) {
                                if (selectedItems.contains(calculation)) {
                                    selectedItems.remove(calculation)
                                } else {
                                    selectedItems.add(calculation)
                                }
                            }
                        },
                        onLongClick = {
                            onLongPress()
                            selectedItems.add(calculation)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectionMode) {
                    Checkbox(
                        checked = selectedItems.contains(calculation),
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                selectedItems.add(calculation)
                            } else {
                                selectedItems.remove(calculation)
                            }
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.onSecondary,
                            uncheckedColor = MaterialTheme.colorScheme.onSecondary,
                            checkmarkColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
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
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSecondary)) {
                                append("=")
                            }
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimary)) {
                                append(calculation.result)
                            }
                        },
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}