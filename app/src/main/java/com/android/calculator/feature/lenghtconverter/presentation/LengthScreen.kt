package com.android.calculator.feature.lenghtconverter.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.android.calculator.actions.LengthAction
import com.android.calculator.utils.ScreenType
import com.android.calculator.ui.common.components.AppBar
import com.android.calculator.ui.common.components.CalculatorGrid
import com.android.calculator.ui.common.components.UnitView
import com.android.calculator.ui.common.factory.ButtonFactory
import com.android.calculator.utils.Constants

@Composable
fun LengthScreen(
    navController: NavHostController,
    modifier: Modifier
) {
    val viewModel = viewModel<LengthViewModel>()
    val state = viewModel.lengthState

    Scaffold(
        topBar = {
            AppBar(screen = ScreenType.Length.screen) {
                navController.navigate(ScreenType.Calculator.route)
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.25f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Top
                ) {
                    val unitList = Constants.LENGTH_UNITS.keys.toMutableSet()

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        UnitView(
                            items = unitList - state.outputUnit,
                            value = state.inputValue,
                            selectedUnit = state.inputUnit,
                            isCurrentView = state.currentView == LengthView.INPUT,
                            onClick = {
                                if (state.currentView != LengthView.INPUT) {
                                    viewModel.onAction(LengthAction.ChangeView(LengthView.INPUT))
                                }
                            },
                            onSelectedUnitChanged = {
                                viewModel.onAction(LengthAction.ChangeInputUnit(it))
                            }
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        UnitView(
                            value = state.outputValue,
                            items = unitList - state.inputUnit,
                            selectedUnit = state.outputUnit,
                            isCurrentView = state.currentView == LengthView.OUTPUT,
                            onClick = {
                                if (state.currentView != LengthView.OUTPUT) {
                                    viewModel.onAction(LengthAction.ChangeView(LengthView.OUTPUT))
                                }
                            },
                            onSelectedUnitChanged = {
                                viewModel.onAction(LengthAction.ChangeOutputUnit(it))
                            }
                        )
                    }
                }
                val buttons = ButtonFactory()
                CalculatorGrid(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp, start = 7.5.dp, end = 7.5.dp),
                    buttons = buttons.getButtons(ScreenType.Length),
                    onAction = viewModel::onAction
                )
            }
        }
    }
}