package com.android.calculator.feature.calculator.main.presentation

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
import com.android.calculator.utils.ScreenType
import com.android.calculator.feature.calculator.main.presentation.components.ActionIconRow
import com.android.calculator.ui.common.components.BottomSheetContainer
import com.android.calculator.feature.calculator.main.presentation.components.CalculationResult
import com.android.calculator.feature.calculator.main.presentation.components.CalculationView
import com.android.calculator.ui.common.components.CalculatorGrid
import com.android.calculator.ui.common.factory.ButtonFactory

@Composable
fun CalculatorMainScreen(
    navController: NavHostController,
    modifier: Modifier,
    isDarkTheme: Boolean,
    onThemeUpdated: () -> Unit

) {
    val viewModel = viewModel<CalculatorMainViewModel>()
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
            onAction = viewModel::onAction,
            isDarkTheme = isDarkTheme,
            onThemeUpdated = onThemeUpdated,
            onNavigate = {
                navController.navigate(it)
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        BottomSheetContainer(state = state, onAction = viewModel::onAction) {
            navController.navigate(it)
        }
        val buttons = ButtonFactory()
        CalculatorGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp, start = 7.5.dp, end = 7.5.dp),
            buttons = buttons.getButtons(ScreenType.CalculatorMain),
            onAction = viewModel::onAction
        )
    }
}