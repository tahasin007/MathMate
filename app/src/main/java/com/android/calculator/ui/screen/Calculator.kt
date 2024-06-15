package com.android.calculator.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.calculator.actions.BaseAction
import com.android.calculator.state.CalculatorState
import com.android.calculator.state.ScreenType
import com.android.calculator.ui.components.CalculationResult
import com.android.calculator.ui.components.CalculationView
import com.android.calculator.ui.components.CalculatorGrid
import com.android.calculator.ui.factory.ButtonFactory

@Composable
fun Calculator(
    state: CalculatorState,
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
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.height(35.dp))
            CalculationResult(state.result)
            CalculationView(state = state)
            val buttons = ButtonFactory()
            CalculatorGrid(
                buttons = buttons.getButtons(ScreenType.CALCULATOR),
                onAction = onAction,
                buttonSpacing = buttonSpacing
            )
        }
    }
}