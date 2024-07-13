package com.android.calculator.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.DiscountAction
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
                    .fillMaxHeight(0.28f)
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
                        label = "Original price",
                        value = state.inputValue,
                        onClick = {
                            Log.i("Discount", "${state.currentView}")
                            if (state.currentView != DiscountView.INPUT) {
                                Log.i("Discount", "OnClick Input")
                                onAction(DiscountAction.ChangeView(DiscountView.INPUT))
                            }
                        },
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
                        label = "Discount (%)",
                        value = state.discountValue,
                        onClick = {
                            Log.i("Discount", "${state.currentView}")
                            if (state.currentView != DiscountView.DISCOUNT) {
                                Log.i("Discount", "OnClick Output")
                                onAction(DiscountAction.ChangeView(DiscountView.DISCOUNT))
                            }
                        },
                        isCurrentView = state.currentView == DiscountView.DISCOUNT
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SimpleUnitView(
                        label = "Final price",
                        value = state.finalValue,
                        onClick = null,
                        isCurrentView = false
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        fontSize = 12.sp,
                        text = "YOU SAVED ${state.savedValue}"
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.72f)
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