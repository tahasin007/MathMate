package com.android.calculator.ui.screen

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.android.calculator.actions.NumeralSystemAction
import com.android.calculator.state.NumeralSystemView
import com.android.calculator.state.ScreenType
import com.android.calculator.state.viewmodel.NumeralSystemViewModel
import com.android.calculator.ui.components.AppBar
import com.android.calculator.ui.components.CalculatorGrid
import com.android.calculator.ui.components.UnitView
import com.android.calculator.ui.factory.ButtonFactory
import com.android.calculator.utils.Constants
import com.android.calculator.utils.NumeralSystem

@Composable
fun NumeralSystemScreen(
    navController: NavHostController,
    modifier: Modifier
) {
    val viewModel = viewModel<NumeralSystemViewModel>()
    val state = viewModel.numeralSystemState

    Scaffold(
        topBar = {
            AppBar(screen = ScreenType.NumeralSystem.screen) {
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
                                value = state.inputValue,
                                items = unitList - state.outputUnit,
                                selectedUnit = state.inputUnit,
                                onClick = {
                                    if (state.currentView != NumeralSystemView.INPUT) {
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
                                isCurrentView = state.currentView == NumeralSystemView.INPUT
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
                                onClick = {
                                    if (state.currentView != NumeralSystemView.OUTPUT) {
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
                                isCurrentView = state.currentView == NumeralSystemView.OUTPUT
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
                        val numeralSystem = if (state.currentView == NumeralSystemView.INPUT) {
                            when (state.inputUnit) {
                                NumeralSystem.Binary::class.simpleName.toString() -> NumeralSystem.Binary
                                NumeralSystem.Octal::class.simpleName.toString() -> NumeralSystem.Octal
                                NumeralSystem.Decimal::class.simpleName.toString() -> NumeralSystem.Decimal
                                else -> NumeralSystem.Hexadecimal
                            }
                        } else {
                            when (state.outputUnit) {
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
                            numeralSystem = numeralSystem
                        )
                    }
                }
            }
        }
    }
}