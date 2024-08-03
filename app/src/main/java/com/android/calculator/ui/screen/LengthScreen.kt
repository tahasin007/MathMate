package com.android.calculator.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.android.calculator.actions.LengthAction
import com.android.calculator.state.LengthView
import com.android.calculator.state.ScreenType
import com.android.calculator.state.viewmodel.LengthViewModel
import com.android.calculator.ui.components.CalculatorGrid
import com.android.calculator.ui.components.UnitView
import com.android.calculator.ui.factory.ButtonFactory
import com.android.calculator.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LengthScreen(
    navController: NavHostController,
    modifier: Modifier
) {
    val viewModel = viewModel<LengthViewModel>()
    val state = viewModel.lengthState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(ScreenType.Length.screen) },
                modifier = Modifier
                    .shadow(
                        elevation = 5.dp,
                        spotColor = MaterialTheme.colorScheme.secondary
                    ),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(ScreenType.Calculator.route)
                        }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            )
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