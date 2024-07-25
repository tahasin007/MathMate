package com.android.calculator.ui.screen

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.android.calculator.actions.DiscountAction
import com.android.calculator.state.DiscountView
import com.android.calculator.state.ScreenType
import com.android.calculator.state.viewmodel.DiscountViewModel
import com.android.calculator.ui.components.CalculatorGridSimple
import com.android.calculator.ui.components.SimpleUnitView
import com.android.calculator.ui.factory.ButtonFactory

@Composable
fun DiscountScreen(
    navController: NavHostController,
    buttonSpacing: Dp = 20.dp,
    modifier: Modifier
) {
    val viewModel = viewModel<DiscountViewModel>()
    val state = viewModel.discountState

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
                            if (state.currentView != DiscountView.INPUT) {
                                viewModel.onAction(DiscountAction.ChangeView(DiscountView.INPUT))
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
                            if (state.currentView != DiscountView.DISCOUNT) {
                                viewModel.onAction(DiscountAction.ChangeView(DiscountView.DISCOUNT))
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
                    buttons = buttons.getButtons(ScreenType.Discount),
                    onAction = viewModel::onAction,
                    buttonSpacing = buttonSpacing
                )
            }
        }
    }
}