package com.android.calculator.feature.numeralsystem.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.calculator.actions.NumeralSystemAction
import com.android.calculator.feature.numeralsystem.domain.model.NumeralSystemView
import com.android.calculator.feature.numeralsystem.presentation.utils.NumeralSystem
import com.android.calculator.feature.settings.domain.model.SettingsState
import com.android.calculator.ui.shared.components.AppBar
import com.android.calculator.ui.shared.components.CalculatorGrid
import com.android.calculator.ui.shared.components.UnitView
import com.android.calculator.ui.shared.factory.ButtonFactory
import com.android.calculator.utils.Constants
import com.android.calculator.utils.ScreenType

@Composable
fun NumeralSystemScreen(
    navController: NavHostController,
    modifier: Modifier,
    configuration: SettingsState,
    viewModel: NumeralSystemViewModel = hiltViewModel()
) {
    val state = viewModel.numeralSystemState

    Scaffold(
        topBar = {
            AppBar(screen = ScreenType.NumeralSystem.screen) {
                navController.navigate(ScreenType.CalculatorMain.route)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val unitList = Constants.NUMERAL_UNITS.keys.toMutableSet()

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                UnitView(
                    value = state.value.inputValue,
                    items = unitList - state.value.outputUnit,
                    selectedUnit = state.value.inputUnit,
                    onClick = {
                        if (state.value.currentView != NumeralSystemView.INPUT) {
                            viewModel.onAction(
                                NumeralSystemAction.ChangeView(NumeralSystemView.INPUT)
                            )
                        }
                    },
                    onSelectedUnitChanged = {
                        viewModel.onAction(NumeralSystemAction.ChangeInputUnit(it))
                    },
                    isCurrentView = state.value.currentView == NumeralSystemView.INPUT,
                    symbol = Constants.NUMERAL_UNITS[state.value.inputUnit]?.symbol
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
                        if (state.value.currentView != NumeralSystemView.OUTPUT) {
                            viewModel.onAction(
                                NumeralSystemAction.ChangeView(NumeralSystemView.OUTPUT)
                            )
                        }
                    },
                    onSelectedUnitChanged = {
                        viewModel.onAction(NumeralSystemAction.ChangeOutputUnit(it))
                    },
                    isCurrentView = state.value.currentView == NumeralSystemView.OUTPUT,
                    symbol = Constants.NUMERAL_UNITS[state.value.outputUnit]?.symbol
                )
            }

            val numeralSystem = remember(state.value.currentView, state.value.inputUnit, state.value.outputUnit) {
                val activeUnit = if (state.value.currentView == NumeralSystemView.INPUT)
                    state.value.inputUnit else state.value.outputUnit
                when (activeUnit) {
                    NumeralSystem.Binary::class.simpleName -> NumeralSystem.Binary
                    NumeralSystem.Octal::class.simpleName -> NumeralSystem.Octal
                    NumeralSystem.Decimal::class.simpleName -> NumeralSystem.Decimal
                    else -> NumeralSystem.Hexadecimal
                }
            }
            val buttons = remember { ButtonFactory() }
            CalculatorGrid(
                buttons = buttons.getButtons(ScreenType.NumeralSystem),
                onAction = viewModel::onAction,
                numeralSystem = numeralSystem,
                configuration = configuration
            )
        }
    }
}