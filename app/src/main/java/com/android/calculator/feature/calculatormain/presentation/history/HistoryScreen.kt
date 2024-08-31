package com.android.calculator.feature.calculatormain.presentation.history

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.calculator.feature.calculatormain.domain.model.Calculation
import com.android.calculator.feature.calculatormain.presentation.history.components.CalculationItems
import com.android.calculator.ui.shared.components.AppBar
import com.android.calculator.ui.shared.components.DeleteItemBottomBar
import com.android.calculator.ui.shared.components.EmptyItemsView
import com.android.calculator.ui.shared.components.TopBarWithSelectedItems
import com.android.calculator.utils.ScreenType

@Composable
fun HistoryScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val groupedCalculations = viewModel.calculations
    val (selectionMode, setSelectionMode) = remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<Calculation>() }
    val isAllItemsSelected = remember { mutableStateOf(false) }

    val totalItems = groupedCalculations.value.values.sumOf { it.size }

    Scaffold(
        topBar = {
            if (selectionMode) {
                TopBarWithSelectedItems(
                    selectedItemSize = selectedItems.size,
                    totalItemSize = totalItems,
                    onDeleteBtnClick = {
                        isAllItemsSelected.value = !isAllItemsSelected.value
                        selectedItems.clear()
                        if (isAllItemsSelected.value) {
                            selectedItems.addAll(groupedCalculations.value.flatMap { it.value })
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
            DeleteItemBottomBar(
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
                    groupedCalculations = groupedCalculations.value,
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