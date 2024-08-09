package com.android.calculator.feature.discount.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.android.calculator.actions.DiscountAction
import com.android.calculator.utils.ScreenType
import com.android.calculator.ui.common.components.AnimatedSlider
import com.android.calculator.ui.common.components.AppBar
import com.android.calculator.ui.common.components.CalculatorGridSimple
import com.android.calculator.ui.common.components.InfoCard
import com.android.calculator.ui.common.components.SimpleUnitView
import com.android.calculator.ui.common.factory.ButtonFactory

@Composable
fun DiscountScreen(
    navController: NavHostController,
    modifier: Modifier
) {
    val viewModel = viewModel<DiscountViewModel>()
    val state = viewModel.discountState

    Scaffold(
        topBar = {
            AppBar(screen = ScreenType.Discount.screen) {
                navController.navigate(ScreenType.Calculator.route)
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            BoxWithConstraints(
                modifier = modifier.fillMaxSize()
            ) {
                val totalHeight = maxHeight
                val firstColumnHeight = totalHeight * 0.4f
                val secondColumnHeight = totalHeight * 0.50f
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .height(firstColumnHeight)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            SimpleUnitView(
                                label = "Original price",
                                value = state.price,
                                onClick = null,
                                isCurrentView = true
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1.5f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            AnimatedSlider(
                                label = "Discount",
                                value = state.discountPercent,
                                onValueChange = {
                                    viewModel.onAction(DiscountAction.EnterDiscountPercent(it.toInt()))
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .padding(16.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1.25f)
                                .fillMaxWidth()
                                .padding(5.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                InfoCard(
                                    modifier = Modifier.weight(0.5f),
                                    label = "FINAL PRICE",
                                    value = state.finalPrice
                                )
                                InfoCard(
                                    modifier = Modifier.weight(0.5f),
                                    label = "YOU SAVED",
                                    value = state.saved
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        modifier = Modifier
                            .height(secondColumnHeight)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        val buttons = ButtonFactory()
                        CalculatorGridSimple(
                            buttons = buttons.getButtons(ScreenType.Discount),
                            onAction = viewModel::onAction
                        )
                    }
                }
            }
        }
    }
}