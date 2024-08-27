package com.android.calculator.feature.numeralsystem.presentation

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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.calculator.CalculatorApplication
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
    app: CalculatorApplication,
    navController: NavHostController,
    modifier: Modifier,
    configuration: SettingsState
) {
    val viewModel = app.numeralSystemViewModel
    val state = viewModel.numeralSystemState

    Scaffold(
        topBar = {
            AppBar(screen = ScreenType.NumeralSystem.screen) {
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
                val firstColumnHeight = totalHeight * 0.25f
                val secondColumnHeight = totalHeight * 0.7f
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
                        val unitList = Constants.NUMERAL_UNITS.toMutableSet()
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
                                            NumeralSystemAction.ChangeView(
                                                NumeralSystemView.INPUT
                                            )
                                        )
                                    }
                                },
                                onSelectedUnitChanged = {
                                    viewModel.onAction(NumeralSystemAction.ChangeInputUnit(it))
                                },
                                isCurrentView = state.value.currentView == NumeralSystemView.INPUT
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
                                            NumeralSystemAction.ChangeView(
                                                NumeralSystemView.OUTPUT
                                            )
                                        )
                                    }
                                },
                                onSelectedUnitChanged = {
                                    viewModel.onAction(NumeralSystemAction.ChangeOutputUnit(it))
                                },
                                isCurrentView = state.value.currentView == NumeralSystemView.OUTPUT
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        modifier = Modifier
                            .height(secondColumnHeight)
                            .fillMaxWidth()
                            .padding(bottom = 20.dp, start = 10.dp, end = 10.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        val numeralSystem =
                            if (state.value.currentView == NumeralSystemView.INPUT) {
                                when (state.value.inputUnit) {
                                    NumeralSystem.Binary::class.simpleName.toString() -> NumeralSystem.Binary
                                    NumeralSystem.Octal::class.simpleName.toString() -> NumeralSystem.Octal
                                    NumeralSystem.Decimal::class.simpleName.toString() -> NumeralSystem.Decimal
                                    else -> NumeralSystem.Hexadecimal
                                }
                            } else {
                                when (state.value.outputUnit) {
                                    NumeralSystem.Binary::class.simpleName.toString() -> NumeralSystem.Binary
                                    NumeralSystem.Octal::class.simpleName.toString() -> NumeralSystem.Octal
                                    NumeralSystem.Decimal::class.simpleName.toString() -> NumeralSystem.Decimal
                                    else -> NumeralSystem.Hexadecimal
                                }
                            }
                        val buttons = ButtonFactory()
                        CalculatorGrid(
                            buttons = buttons.getButtons(ScreenType.NumeralSystem),
                            onAction = viewModel::onAction,
                            numeralSystem = numeralSystem,
                            configuration = configuration
                        )
                    }
                }
            }
        }
    }
}