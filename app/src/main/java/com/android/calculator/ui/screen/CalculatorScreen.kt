package com.android.calculator.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.android.calculator.state.ScreenType
import com.android.calculator.state.viewmodel.CalculatorViewModel
import com.android.calculator.ui.components.ActionIconRow
import com.android.calculator.ui.components.BottomSheetContainer
import com.android.calculator.ui.components.CalculationResult
import com.android.calculator.ui.components.CalculationView
import com.android.calculator.ui.components.CalculatorGrid
import com.android.calculator.ui.factory.ButtonFactory

@Composable
fun CalculatorScreen(
    navController: NavHostController,
    buttonSpacing: Dp = 10.dp,
    modifier: Modifier
) {
    val viewModel = viewModel<CalculatorViewModel>()
    val state = viewModel.calculatorState

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.height(35.dp))
            CalculationResult(result = state.result)
            CalculationView(state = state)
            ActionIconRow(
                state = state,
                onAction = viewModel::onAction
            )
            BottomSheetContainer(state = state, onAction = viewModel::onAction) {
                navController.navigate(it)
            }
            val buttons = ButtonFactory()
            CalculatorGrid(
                buttons = buttons.getButtons(ScreenType.Calculator),
                onAction = viewModel::onAction,
                buttonSpacing = buttonSpacing
            )
        }
    }
}