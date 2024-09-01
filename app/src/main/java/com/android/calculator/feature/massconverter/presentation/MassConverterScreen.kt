package com.android.calculator.feature.massconverter.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.calculator.actions.MassAction
import com.android.calculator.feature.massconverter.domain.model.MassView
import com.android.calculator.feature.settings.domain.model.SettingsState
import com.android.calculator.ui.shared.components.AppBar
import com.android.calculator.ui.shared.components.CalculatorGrid
import com.android.calculator.ui.shared.components.UnitView
import com.android.calculator.ui.shared.factory.ButtonFactory
import com.android.calculator.utils.Constants
import com.android.calculator.utils.ScreenType

@Composable
fun MassConverterScreen(
    navController: NavHostController,
    modifier: Modifier,
    configuration: SettingsState,
    viewModel: MassConverterViewModel = hiltViewModel()
) {
    val state = viewModel.massState

    Scaffold(
        topBar = {
            AppBar(screen = ScreenType.Mass.screen) {
                navController.navigate(ScreenType.CalculatorMain.route)
            }
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val totalHeight = maxHeight
            val firstColumnHeight = totalHeight * 0.2f
            val secondColumnHeight = totalHeight * 0.65f

            Column(modifier = Modifier.fillMaxSize()) {
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
                        val unitList = Constants.MASS_UNITS.keys.toMutableSet()

                        UnitView(
                            items = unitList - state.value.outputUnit,
                            value = state.value.inputValue,
                            selectedUnit = state.value.inputUnit,
                            isCurrentView = state.value.currentView == MassView.INPUT,
                            onClick = {
                                if (state.value.currentView != MassView.INPUT) {
                                    viewModel.onAction(MassAction.ChangeView(MassView.INPUT))
                                }
                            },
                            onSelectedUnitChanged = {
                                viewModel.onAction(MassAction.ChangeInputUnit(it))
                            },
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
                            value = state.value.outputValue,
                            items = unitList - state.value.inputUnit,
                            selectedUnit = state.value.outputUnit,
                            isCurrentView = state.value.currentView == MassView.OUTPUT,
                            onClick = {
                                if (state.value.currentView != MassView.OUTPUT) {
                                    viewModel.onAction(MassAction.ChangeView(MassView.OUTPUT))
                                }
                            },
                            onSelectedUnitChanged = {
                                viewModel.onAction(MassAction.ChangeOutputUnit(it))
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier
                        .height(secondColumnHeight)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    CalculatorGrid(
                        modifier = Modifier.fillMaxWidth(),
                        buttons = ButtonFactory().getButtons(ScreenType.Mass),
                        onAction = viewModel::onAction,
                        configuration = configuration
                    )
                }
            }
        }
    }
}