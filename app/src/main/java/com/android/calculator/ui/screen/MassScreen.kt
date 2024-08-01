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
import com.android.calculator.actions.MassAction
import com.android.calculator.state.MassView
import com.android.calculator.state.ScreenType
import com.android.calculator.state.viewmodel.MassViewModel
import com.android.calculator.ui.components.CalculatorGrid
import com.android.calculator.ui.components.UnitView
import com.android.calculator.ui.factory.ButtonFactory
import com.android.calculator.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MassScreen(
    navController: NavHostController,
    modifier: Modifier
) {
    val viewModel = viewModel<MassViewModel>()
    val state = viewModel.massState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(ScreenType.Mass.screen) },
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
                    IconButton(onClick = {
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
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        val unitList = Constants.MASS_UNITS.keys.toMutableSet()

                        UnitView(
                            items = unitList - state.outputUnit,
                            value = state.inputValue,
                            selectedUnit = state.inputUnit,
                            isCurrentView = state.currentView == MassView.INPUT,
                            onClick = {
                                if (state.currentView != MassView.INPUT) {
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
                            value = state.outputValue,
                            items = unitList - state.inputUnit,
                            selectedUnit = state.outputUnit,
                            isCurrentView = state.currentView == MassView.OUTPUT,
                            onClick = {
                                if (state.currentView != MassView.OUTPUT) {
                                    viewModel.onAction(MassAction.ChangeView(MassView.OUTPUT))
                                }
                            },
                            onSelectedUnitChanged = {
                                viewModel.onAction(MassAction.ChangeOutputUnit(it))
                            }
                        )
                    }
                }
                val buttons = ButtonFactory()
                CalculatorGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp, start = 7.5.dp, end = 7.5.dp),
                    buttons = buttons.getButtons(ScreenType.Mass),
                    onAction = viewModel::onAction
                )
            }
        }
    }
}