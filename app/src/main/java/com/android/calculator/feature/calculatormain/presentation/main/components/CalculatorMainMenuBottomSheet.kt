package com.android.calculator.feature.calculatormain.presentation.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.CalculatorAction
import com.android.calculator.feature.calculatormain.presentation.main.CalculatorMainState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorMainMenuBottomSheet(
    state: CalculatorMainState,
    onAction: (BaseAction) -> Unit,
    onNavigate: (screen: String) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()

    if (state.isConverterSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                onAction(CalculatorAction.ConverterMenuVisibility(false))
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
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                MenuUnitConverter(
                    onNavigate = onNavigate,
                    onAction = onAction
                )
                Spacer(modifier = Modifier.height(50.dp))
                MenuSavedItem(
                    onNavigate = onNavigate,
                    onAction = onAction
                )
            }
        }
    }
}