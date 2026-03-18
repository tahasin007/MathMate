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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.calculator.actions.CalculatorAction
import com.android.calculator.feature.calculatormain.presentation.main.components.ActionIconRow
import com.android.calculator.feature.calculatormain.presentation.main.components.CalculationResult
import com.android.calculator.feature.calculatormain.presentation.main.components.CalculationView
import com.android.calculator.feature.calculatormain.presentation.main.components.CalculatorMainMenuBottomSheet
import com.android.calculator.feature.calculatormain.presentation.main.components.SaveCalculationBottomSheet
import com.android.calculator.feature.settings.domain.model.SettingsState
import com.android.calculator.ui.shared.components.CalculatorGrid
import com.android.calculator.ui.shared.factory.ButtonFactory
import com.android.calculator.utils.ScreenType
import kotlinx.coroutines.launch

@Composable
fun CalculatorMainScreen(
    navController: NavHostController,
    modifier: Modifier,
    isDarkTheme: Boolean,
    onThemeUpdated: () -> Unit,
    configuration: SettingsState,
    viewModel: CalculatorMainViewModel = hiltViewModel()
) {
    val state = viewModel.calculatorState

    val context = LocalContext.current
    val clipboardManager: ClipboardManager =
        context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = hostState) {
                Snackbar(
                    snackbarData = it,
                    containerColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.75f),
                    contentColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { innerPadding ->
        Column(
            // Arrangement.Top + a weighted Spacer before the grid
            modifier = modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            CalculationResult(
                result = state.result,
                onCopyClick = {
                    val clip = ClipData.newPlainText("Copied Answer", state.result)
                    clipboardManager.setPrimaryClip(clip)
                    scope.launch {
                        hostState.showSnackbar("Answer Copied to Clipboard")
                    }
                },
                onBookmarkClick = {
                    viewModel.onAction(CalculatorAction.SaveCalculationMenuVisibility(true))
                }
            )

            CalculationView(state = state)

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

            CalculatorMainMenuBottomSheet(state = state, onAction = viewModel::onAction) {
                navController.navigate(it)
            }

            // Guarantees the grid is never pushed over the rows above it.
            Spacer(modifier = Modifier.weight(1f))

            val buttons = remember { ButtonFactory() }
            CalculatorGrid(
                modifier = Modifier.fillMaxWidth(),
                buttons = buttons.getButtons(ScreenType.CalculatorMain),
                onAction = viewModel::onAction,
                configuration = configuration
            )
        }
    }
}