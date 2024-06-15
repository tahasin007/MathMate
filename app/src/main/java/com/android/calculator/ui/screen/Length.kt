package com.android.calculator.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.LengthAction
import com.android.calculator.state.LengthState
import com.android.calculator.state.ScreenType
import com.android.calculator.ui.components.CalculatorGrid
import com.android.calculator.ui.components.LengthUnitView
import com.android.calculator.ui.factory.ButtonFactory
import com.android.calculator.utils.Constants

@Composable
fun Length(
    state: LengthState,
    buttonSpacing: Dp,
    modifier: Modifier,
    onAction: (BaseAction) -> Unit
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxHeight(0.25f) // 50% height for both LengthUnitViews
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    LengthUnitView(
                        items = Constants.LENGTH_UNITS,
                        value = state.inputValue,
                        selectedUnit = state.inputUnit,
                        onClick = {
                            onAction(LengthAction.ChangeView)
                        }
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    LengthUnitView(
                        items = Constants.LENGTH_UNITS,
                        value = state.outputValue,
                        selectedUnit = state.outputUnit,
                        onClick = {
                            onAction(LengthAction.ChangeView)
                        }
                    )
                }
            }
            val buttons = ButtonFactory()
            CalculatorGrid(
                buttons = buttons.getButtons(ScreenType.LENGTH),
                onAction = onAction,
                buttonSpacing = buttonSpacing
            )
        }
    }
}