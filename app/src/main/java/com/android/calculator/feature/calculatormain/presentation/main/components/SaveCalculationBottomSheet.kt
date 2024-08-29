package com.android.calculator.feature.calculatormain.presentation.main.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.CalculatorAction
import com.android.calculator.feature.calculatormain.presentation.main.CalculatorMainState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveCalculationBottomSheet(
    state: CalculatorMainState,
    onAction: (BaseAction) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    var calculationName by remember { mutableStateOf("") }

    if (state.isSaveCalculationSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                onAction(CalculatorAction.SaveCalculationMenuVisibility(false))
            },
            sheetState = bottomSheetState,
            containerColor = MaterialTheme.colorScheme.primary,
            dragHandle = {
                Spacer(modifier = Modifier)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Save Calculation",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    IconButton(onClick = {
                        onAction(CalculatorAction.SaveBookmark(calculationName))
                        onAction(CalculatorAction.SaveCalculationMenuVisibility(false))
                    }, enabled = calculationName.isNotEmpty()) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Save",
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = calculationName,
                    onValueChange = {
                        calculationName = it
                    },
                    placeholder = {
                        Text(text = "Calculation Name")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            color = if (calculationName.isEmpty()) MaterialTheme.colorScheme.onSecondary.copy(
                                alpha = 0.25f
                            )
                            else MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.75f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.primary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = .75f),
                        cursorColor = MaterialTheme.colorScheme.onSecondary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary.copy(.75f),
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary.copy(.75f)
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}