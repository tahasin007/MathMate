package com.android.calculator.feature.discountcalculator.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.calculator.actions.DiscountAction
import com.android.calculator.feature.settings.domain.model.SettingsState
import com.android.calculator.ui.shared.components.AnimatedSlider
import com.android.calculator.ui.shared.components.AppBar
import com.android.calculator.ui.shared.components.CalculatorGridSimple
import com.android.calculator.ui.shared.components.InfoCard
import com.android.calculator.ui.shared.components.SimpleUnitView
import com.android.calculator.ui.shared.factory.ButtonFactory
import com.android.calculator.utils.ScreenType

@Composable
fun DiscountScreen(
    navController: NavHostController,
    modifier: Modifier,
    configuration: SettingsState,
    viewModel: DiscountCalculatorViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Scaffold(
        topBar = {
            AppBar(screen = ScreenType.Discount.screen) {
                navController.navigate(ScreenType.CalculatorMain.route)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // weight(1f) lets it expand to fill all space the grid doesn't need.
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SimpleUnitView(
                        label = "Original price",
                        value = state.value.price,
                        onClick = null,
                        isCurrentView = true
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1.5f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedSlider(
                        label = "Discount",
                        value = state.value.discountPercent,
                        onValueChange = {
                            viewModel.onAction(DiscountAction.EnterDiscountPercent(it.toInt()))
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .padding(16.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1.25f)
                        .fillMaxWidth()
                        .padding(5.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        InfoCard(
                            modifier = Modifier.weight(0.5f),
                            label = "FINAL PRICE",
                            value = state.value.finalPrice
                        )
                        InfoCard(
                            modifier = Modifier.weight(0.5f),
                            label = "YOU SAVED",
                            value = state.value.saved
                        )
                    }
                }
            }

            val buttons = remember { ButtonFactory() }
            CalculatorGridSimple(
                buttons = buttons.getButtons(ScreenType.Discount),
                onAction = viewModel::onAction,
                configuration = configuration
            )
        }
    }
}