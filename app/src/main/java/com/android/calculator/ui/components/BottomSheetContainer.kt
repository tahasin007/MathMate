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
import com.android.calculator.state.ScreenType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContainer(
    state: CalculatorState,
    onAction: (BaseAction) -> Unit,
    onNavigate: (screen: String) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()

    if (state.isBottomSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                onAction(CalculatorAction.BottomSheetVisibility(false))
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
                    ConverterIconView(R.drawable.ic_area, ScreenType.Length, Modifier.weight(1f)) {
                        onNavigate.invoke(ScreenType.Length.route)
                        onAction(CalculatorAction.BottomSheetVisibility(false))
                    }
                    ConverterIconView(R.drawable.ic_mass, ScreenType.Mass, Modifier.weight(1f)) {
                        onNavigate.invoke(ScreenType.Mass.route)
                        onAction(CalculatorAction.BottomSheetVisibility(false))
                    }
                    ConverterIconView(
                        R.drawable.ic_discount,
                        ScreenType.Discount,
                        Modifier.weight(1f)
                    ) {
                        onNavigate.invoke(ScreenType.Discount.route)
                        onAction(CalculatorAction.BottomSheetVisibility(false))
                    }
                }
            }
        }
    }
}