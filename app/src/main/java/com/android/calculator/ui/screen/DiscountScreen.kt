package com.android.calculator.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.android.calculator.actions.DiscountAction
import com.android.calculator.state.DiscountView
import com.android.calculator.state.ScreenType
import com.android.calculator.state.viewmodel.DiscountViewModel
import com.android.calculator.ui.components.CalculatorGridSimple
import com.android.calculator.ui.components.SimpleUnitView
import com.android.calculator.ui.factory.ButtonFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscountScreen(
    navController: NavHostController,
    modifier: Modifier
) {
    val viewModel = viewModel<DiscountViewModel>()
    val state = viewModel.discountState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(ScreenType.Discount.screen) },
                modifier = Modifier
                    .shadow(
                        elevation = 2.5.dp,
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
                            contentDescription = ScreenType.Discount.screen,
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
            BoxWithConstraints(
                modifier = modifier.fillMaxSize()
            ) {
                val totalHeight = maxHeight
                val firstColumnHeight = totalHeight * 0.35f
                val secondColumnHeight = totalHeight * 0.52f
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .height(firstColumnHeight)
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
                                value = state.inputValue,
                                onClick = {
                                    if (state.currentView != DiscountView.INPUT) {
                                        viewModel.onAction(DiscountAction.ChangeView(DiscountView.INPUT))
                                    }
                                },
                                isCurrentView = state.currentView == DiscountView.INPUT
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            SimpleUnitView(
                                label = "Discount (%)",
                                value = state.discountValue,
                                onClick = {
                                    if (state.currentView != DiscountView.DISCOUNT) {
                                        viewModel.onAction(DiscountAction.ChangeView(DiscountView.DISCOUNT))
                                    }
                                },
                                isCurrentView = state.currentView == DiscountView.DISCOUNT
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            SimpleUnitView(
                                label = "Final price",
                                value = state.finalValue,
                                onClick = null,
                                isCurrentView = false
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                fontSize = 12.sp,
                                text = "YOU SAVED ${state.savedValue}"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        modifier = Modifier
                            .height(secondColumnHeight)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        val buttons = ButtonFactory()
                        CalculatorGridSimple(
                            buttons = buttons.getButtons(ScreenType.Discount),
                            onAction = viewModel::onAction
                        )
                    }
                }
            }
        }
    }
}