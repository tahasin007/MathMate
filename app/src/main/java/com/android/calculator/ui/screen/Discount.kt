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
import com.android.calculator.state.DiscountState
import com.android.calculator.state.DiscountView
import com.android.calculator.state.ScreenType
import com.android.calculator.ui.components.CalculatorGridSimple
import com.android.calculator.ui.components.SimpleUnitView
import com.android.calculator.ui.factory.ButtonFactory

@Composable
fun Discount(
    state: DiscountState,
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
                    .fillMaxHeight(0.25f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SimpleUnitView(
                        value = state.inputValue,
                        onClick = { /*TODO*/ },
                        isCurrentView = state.currentView == DiscountView.INPUT
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SimpleUnitView(
                        value = state.inputValue,
                        onClick = { /*TODO*/ },
                        isCurrentView = state.currentView == DiscountView.OUTPUT
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.75f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom
            ) {
                val buttons = ButtonFactory()
                CalculatorGridSimple(
                    buttons = buttons.getButtons(ScreenType.DISCOUNT),
                    onAction = onAction,
                    buttonSpacing = buttonSpacing
                )
            }
        }
    }
}