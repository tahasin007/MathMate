package com.android.calculator.feature.calculatormain.presentation.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.calculator.CalculatorApplication
import com.android.calculator.actions.CalculatorAction
import com.android.calculator.feature.calculatormain.presentation.main.components.ActionIconRow
import com.android.calculator.feature.calculatormain.presentation.main.components.CalculationResult
import com.android.calculator.feature.calculatormain.presentation.main.components.CalculationView
import com.android.calculator.feature.calculatormain.presentation.main.components.ConverterBottomSheet
import com.android.calculator.feature.calculatormain.presentation.main.components.SaveCalculationBottomSheet
import com.android.calculator.feature.settings.domain.model.SettingsState
import com.android.calculator.ui.shared.components.CalculatorGrid
import com.android.calculator.ui.shared.factory.ButtonFactory
import com.android.calculator.utils.ScreenType
import kotlinx.coroutines.launch

@Composable
fun CalculatorMainScreen(
    app: CalculatorApplication,
    navController: NavHostController,
    modifier: Modifier,
    isDarkTheme: Boolean,
    onThemeUpdated: () -> Unit,
    configuration: SettingsState
) {
    val viewModel = app.calculatorMainViewModel
    val state = viewModel.calculatorState

    val context = LocalContext.current
    val clipboardManager: ClipboardManager =
        context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(
                    snackbarData = it,
                    containerColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.75f),
                    contentColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.height(35.dp))
            CalculationResult(result = state.result)

            CalculationView(
                state = state,
                onCopyClick = {
                    if (state.result.isNotEmpty()) {
                        val clip = ClipData.newPlainText("Copied Answer", state.result)
                        clipboardManager.setPrimaryClip(clip)

                        scope.launch {
                            snackbarHostState.showSnackbar("Answer Copied to Clipboard")
                        }
                    }
                },
                onBookmarkClick = {
                    viewModel.onAction(CalculatorAction.SaveCalculationMenuVisibility(true))
                })

            SaveCalculationBottomSheet(state = state, onAction = viewModel::onAction)

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
            ConverterBottomSheet(state = state, onAction = viewModel::onAction) {
                navController.navigate(it)
            }

            val buttons = ButtonFactory()
            CalculatorGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, start = 7.5.dp, end = 7.5.dp),
                buttons = buttons.getButtons(ScreenType.CalculatorMain),
                onAction = viewModel::onAction,
                configuration = configuration
            )
        }
    }
}