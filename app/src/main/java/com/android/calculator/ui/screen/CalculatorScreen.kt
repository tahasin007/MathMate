package com.android.calculator.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    modifier: Modifier
) {
    val viewModel = viewModel<CalculatorViewModel>()
    val state = viewModel.calculatorState

    Column(
        modifier = modifier,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp, start = 7.5.dp, end = 7.5.dp),
            buttons = buttons.getButtons(ScreenType.Calculator),
            onAction = viewModel::onAction
        )
    }
}