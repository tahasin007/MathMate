package com.android.calculator.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.CalculatorAction
import com.android.calculator.state.CalculatorState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContainer(
    state: CalculatorState,
    onAction: (BaseAction) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()

    if (state.isBottomSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                onAction(CalculatorAction.BottomSheetVisibility)
            },
            sheetState = bottomSheetState,
            containerColor = Color.White,
        ) {

        }
    }
}