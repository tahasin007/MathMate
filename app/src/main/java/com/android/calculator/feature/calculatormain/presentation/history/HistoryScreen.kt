package com.android.calculator.feature.calculatormain.presentation.history

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.calculator.CalculatorApplication
import com.android.calculator.feature.calculatormain.presentation.history.components.CalculationItem
import com.android.calculator.ui.common.components.AppBar
import com.android.calculator.utils.ScreenType

@Composable
fun HistoryScreen(
    app: CalculatorApplication,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel = app.historyViewModel
    val groupedCalculations = viewModel.groupedCalculations

    Scaffold(
        topBar = {
            AppBar(screen = ScreenType.History.screen) {
                navController.navigate(ScreenType.CalculatorMain.route)
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn(
                modifier = modifier
                    .padding(horizontal = 10.dp)
                    .padding(vertical = 8.dp)
            ) {
                groupedCalculations.forEach { (date, calculations) ->
                    item {
                        CalculationItem(date, calculations)

                        if (groupedCalculations.keys.last() != date) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 5.dp),
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}