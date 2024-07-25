package com.android.calculator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.calculator.R
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
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Unit Converter", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(25.dp))
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    ConverterIconView(R.drawable.ic_area, "Area", Modifier.weight(1f))
                    ConverterIconView(R.drawable.ic_mass, "Mass", Modifier.weight(1f))
                    ConverterIconView(R.drawable.ic_discount, "Discount", Modifier.weight(1f))
                }
            }
        }
    }
}