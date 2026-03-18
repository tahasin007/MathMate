package com.android.calculator.feature.currencyconverter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.calculator.actions.CurrencyAction
import com.android.calculator.feature.currencyconverter.domain.model.CurrencyView
import com.android.calculator.feature.currencyconverter.presentation.component.CurrencyInfoItem
import com.android.calculator.feature.currencyconverter.presentation.component.CurrencySwapIcon
import com.android.calculator.feature.settings.domain.model.SettingsState
import com.android.calculator.ui.shared.components.AppBar
import com.android.calculator.ui.shared.components.CalculatorGridSimple
import com.android.calculator.ui.shared.factory.ButtonFactory
import com.android.calculator.utils.ScreenType

@Composable
fun CurrencyConverterScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    configuration: SettingsState,
    viewModel: CurrencyConverterViewModel = hiltViewModel()
) {
    val state = viewModel.currencyState

    Scaffold(topBar = {
        AppBar(screen = ScreenType.Currency.screen) {
            navController.navigate(ScreenType.CalculatorMain.route)
        }
    }) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // weight(1f) gives it all space the grid doesn't need, so the grid
            // is never pushed off-screen regardless of phone height.
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Last Updated: ")
                        withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                            append(state.value.lastUpdatedInLocalTime)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = .75f),
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.onSecondary.copy(alpha = .5f))
                        .padding(all = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    CurrencyInfoItem(
                        selectedCurrency = state.value.fromCurrency,
                        currencyValue = state.value.fromValue,
                        isCurrentView = state.value.currentView == CurrencyView.FROM,
                        exchangeRate = state.value.fromToExchangeRate,
                        onClick = {
                            if (state.value.currentView != CurrencyView.FROM) {
                                viewModel.onAction(CurrencyAction.ChangeView(CurrencyView.FROM))
                            }
                        },
                        onSelectedUnitChanged = {
                            viewModel.onAction(CurrencyAction.ChangeFromUnit(it))
                        }
                    )

                    Spacer(modifier = Modifier.height(30.dp))
                    CurrencySwapIcon(onSwapClicked = {
                        viewModel.onAction(CurrencyAction.SwitchView)
                    })

                    CurrencyInfoItem(
                        selectedCurrency = state.value.toCurrency,
                        currencyValue = state.value.toValue,
                        isCurrentView = state.value.currentView == CurrencyView.TO,
                        exchangeRate = state.value.toFromExchangeRate,
                        onClick = {
                            if (state.value.currentView != CurrencyView.TO) {
                                viewModel.onAction(CurrencyAction.ChangeView(CurrencyView.TO))
                            }
                        },
                        onSelectedUnitChanged = {
                            viewModel.onAction(CurrencyAction.ChangeToUnit(it))
                        }
                    )
                }
            }

            val buttons = remember { ButtonFactory() }
            CalculatorGridSimple(
                buttons = buttons.getButtons(ScreenType.Currency),
                onAction = viewModel::onAction,
                configuration = configuration
            )
        }
    }
}