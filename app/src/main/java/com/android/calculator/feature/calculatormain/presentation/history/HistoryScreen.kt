package com.android.calculator.feature.calculatormain.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.calculator.CalculatorApplication
import com.android.calculator.feature.calculatormain.domain.model.Calculation
import com.android.calculator.feature.calculatormain.presentation.history.components.CalculationItem
import com.android.calculator.feature.calculatormain.presentation.history.components.HistoryBottomBar
import com.android.calculator.feature.calculatormain.presentation.history.components.HistoryTopBar
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
            if (totalItems == 0) {
                LazyColumn(
                    modifier = modifier
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 8.dp)
                ) {
                    groupedCalculations.forEach { (date, calculations) ->
                        item {
                            CalculationItem(
                                date = date,
                                calculations = calculations,
                                selectionMode = selectionMode,
                                selectedItems = selectedItems,
                                onLongPress = { setSelectionMode(true) }
                            )

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
            } else {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = .5f),
                        modifier = Modifier
                            .size(64.dp)
                            .padding(bottom = 24.dp)
                    )

                    Text(
                        text = "No items here yet",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = .5f)
                    )
                }
            }
        }
    }
}