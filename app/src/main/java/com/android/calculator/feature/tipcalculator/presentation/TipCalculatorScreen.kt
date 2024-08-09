package com.android.calculator.feature.tipcalculator.presentation

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.android.calculator.actions.TipCalculatorAction
import com.android.calculator.utils.ScreenType
import com.android.calculator.ui.common.components.AnimatedSlider
import com.android.calculator.ui.common.components.AppBar
import com.android.calculator.ui.common.components.CalculatorGridSimple
import com.android.calculator.ui.common.components.InfoCard
import com.android.calculator.ui.common.components.NumberCounter
import com.android.calculator.ui.common.components.SimpleUnitView
import com.android.calculator.ui.common.factory.ButtonFactory

@Composable
fun TipCalculatorScreen(
    navController: NavHostController,
    modifier: Modifier
) {
    val viewModel = viewModel<TipCalculatorViewModel>()
    val state = viewModel.tipCalculatorState

    Scaffold(
        topBar = {
            AppBar(screen = ScreenType.TipCalculator.screen) {
                navController.navigate(ScreenType.CalculatorMain.route)
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
                val firstColumnHeight = totalHeight * 0.45f
                val secondColumnHeight = totalHeight * 0.5f
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .height(firstColumnHeight)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(.75f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            SimpleUnitView(
                                label = "Total Bill",
                                value = state.bill,
                                onClick = null,
                                isCurrentView = true
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(.75f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Split",
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    fontSize = 20.sp,
                                    modifier = Modifier.weight(0.6f)
                                )
                                NumberCounter(
                                    modifier = Modifier.weight(0.4f),
                                    onValueChange = {
                                        viewModel.onAction(TipCalculatorAction.EnterHeadCount(it))
                                    }
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1.5f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            AnimatedSlider(
                                label = "Tip",
                                value = state.tipPercentage,
                                onValueChange = {
                                    viewModel.onAction(TipCalculatorAction.EnterTipPercent(it.toInt()))
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
                                    label = "TOTAL",
                                    value = state.totalBill
                                )
                                InfoCard(
                                    modifier = Modifier.weight(0.5f),
                                    label = "P/PERSON",
                                    value = state.totalPerHead
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
                            buttons = buttons.getButtons(ScreenType.TipCalculator),
                            onAction = viewModel::onAction
                        )
                    }
                }
            }
        }
    }
}
