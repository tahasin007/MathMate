package com.android.calculator.feature.calculatormain.presentation.history.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.calculator.feature.calculatormain.domain.model.Calculation

@Composable
fun CalculationItems(
    modifier: Modifier = Modifier,
    groupedCalculations: Map<String, List<Calculation>>,
    selectionMode: Boolean,
    selectedItems: SnapshotStateList<Calculation>,
    onLongPress: () -> Unit
) {
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
                    onLongPress = onLongPress
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
}