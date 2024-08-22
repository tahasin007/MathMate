package com.android.calculator.feature.calculatormain.presentation.history

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.android.calculator.CalculatorApplication
import com.android.calculator.feature.calculatormain.domain.model.Calculation
import com.android.calculator.feature.calculatormain.presentation.history.components.CalculationItems
import com.android.calculator.feature.calculatormain.presentation.history.components.EmptyItemsView
import com.android.calculator.feature.calculatormain.presentation.history.components.HistoryBottomBar
import com.android.calculator.feature.calculatormain.presentation.history.components.HistoryTopBar
import com.android.calculator.ui.shared.components.AppBar
import com.android.calculator.utils.ScreenType

@Composable
fun HistoryScreen(
    app: CalculatorApplication,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel = app.historyViewModel
    val groupedCalculations = viewModel.groupedCalculations
    val (selectionMode, setSelectionMode) = remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<Calculation>() }
    val isAllItemsSelected = remember { mutableStateOf(false) }

    val totalItems = groupedCalculations.values.sumOf { it.size }

    Scaffold(
        topBar = {
            if (selectionMode) {
                HistoryTopBar(
                    selectedItemSize = selectedItems.size,
                    totalItemSize = totalItems,
                    onDeleteBtnClick = {
                        isAllItemsSelected.value = !isAllItemsSelected.value
                        selectedItems.clear()
                        if (isAllItemsSelected.value) {
                            selectedItems.addAll(groupedCalculations.flatMap { it.value })
                        }
                    },
                    onNavigationBtnClick = {
                        setSelectionMode(false)
                        selectedItems.clear()
                    },
                )
            } else {
                AppBar(screen = ScreenType.History.screen) {
                    navController.navigate(ScreenType.CalculatorMain.route)
                }
            }
        },
        bottomBar = {
            HistoryBottomBar(
                selectionMode = selectionMode,
                onDelete = {
                    viewModel.deleteSelectedCalculations(selectedItems)
                    setSelectionMode(false)
                    selectedItems.clear()
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (totalItems > 0) {
                CalculationItems(
                    modifier = modifier,
                    groupedCalculations = groupedCalculations,
                    selectionMode = selectionMode,
                    selectedItems = selectedItems,
                    onLongPress = { setSelectionMode(true) }
                )
            } else {
                EmptyItemsView(modifier = modifier)
            }
        }
    }
}