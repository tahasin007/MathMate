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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.android.calculator.actions.LengthAction
import com.android.calculator.state.LengthView
import com.android.calculator.state.MassView
import com.android.calculator.state.ScreenType
import com.android.calculator.state.viewmodel.MassViewModel
import com.android.calculator.ui.components.CalculatorGrid
import com.android.calculator.ui.components.UnitView
import com.android.calculator.ui.factory.ButtonFactory
import com.android.calculator.utils.Constants

@Composable
fun MassScreen(
    navController: NavHostController,
    buttonSpacing: Dp = 10.dp,
    modifier: Modifier
) {
    val viewModel = viewModel<MassViewModel>()
    val state = viewModel.massState

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
                    val unitList = Constants.MASS_UNITS.keys.toMutableSet()

                    UnitView(
                        items = unitList,
                        value = state.inputValue,
                        selectedUnit = state.inputUnit,
                        isCurrentView = state.currentView == MassView.INPUT,
                        onClick = {
                            if (state.currentView != MassView.INPUT) {
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
                    val unitList = Constants.MASS_UNITS.keys.toMutableSet()

                    UnitView(
                        value = state.outputValue,
                        items = unitList,
                        selectedUnit = state.outputUnit,
                        isCurrentView = state.currentView == MassView.OUTPUT,
                        onClick = {
                            if (state.currentView != MassView.OUTPUT) {
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
                buttons = buttons.getButtons(ScreenType.Mass),
                onAction = viewModel::onAction,
                buttonSpacing = buttonSpacing
            )
        }
    }
}