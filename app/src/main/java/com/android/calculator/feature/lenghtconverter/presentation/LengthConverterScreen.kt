package com.android.calculator.feature.lenghtconverter.presentation

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
import com.android.calculator.actions.LengthAction
import com.android.calculator.feature.lenghtconverter.domain.model.LengthView
import com.android.calculator.feature.settings.domain.model.SettingsState
import com.android.calculator.ui.shared.components.AppBar
import com.android.calculator.ui.shared.components.CalculatorGrid
import com.android.calculator.ui.shared.components.UnitView
import com.android.calculator.ui.shared.factory.ButtonFactory
import com.android.calculator.utils.Constants
import com.android.calculator.utils.ScreenType

@Composable
fun LengthScreen(
    navController: NavHostController,
    modifier: Modifier,
    configuration: SettingsState,
    viewModel: LengthConverterViewModel = hiltViewModel()
) {
    val state = viewModel.lengthState

    Scaffold(
        topBar = {
            AppBar(screen = ScreenType.Length.screen) {
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
                            items = unitList - state.value.outputUnit,
                            value = state.value.inputValue,
                            selectedUnit = state.value.inputUnit,
                            isCurrentView = state.value.currentView == LengthView.INPUT,
                            onClick = {
                                if (state.value.currentView != LengthView.INPUT) {
                                    viewModel.onAction(LengthAction.ChangeView(LengthView.INPUT))
                                }
                            },
                            onSelectedUnitChanged = {
                                viewModel.onAction(LengthAction.ChangeInputUnit(it))
                            },
                            symbol = Constants.LENGTH_UNITS[state.value.inputUnit]?.symbol
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        UnitView(
                            value = state.value.outputValue,
                            items = unitList - state.value.inputUnit,
                            selectedUnit = state.value.outputUnit,
                            onClick = {
                                if (state.value.currentView != LengthView.OUTPUT) {
                                    viewModel.onAction(LengthAction.ChangeView(LengthView.OUTPUT))
                                }
                            },
                            onSelectedUnitChanged = {
                                viewModel.onAction(LengthAction.ChangeOutputUnit(it))
                            },
                            isCurrentView = state.value.currentView == LengthView.OUTPUT,
                            symbol = Constants.LENGTH_UNITS[state.value.outputUnit]?.symbol
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
                        buttons = ButtonFactory().getButtons(ScreenType.Length),
                        onAction = viewModel::onAction,
                        configuration = configuration
                    )
                }
            }
        }
    }
}