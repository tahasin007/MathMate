package com.android.calculator.feature.settings.presentaiton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.calculator.actions.SettingsAction
import com.android.calculator.feature.settings.presentaiton.component.ColorThemePopup
import com.android.calculator.feature.settings.presentaiton.component.TextViewWithColorCircle
import com.android.calculator.feature.settings.presentaiton.component.TextViewWithSwitch
import com.android.calculator.ui.shared.components.AppBar
import com.android.calculator.utils.ScreenType

@Composable
fun SettingsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    var showPopup by remember { mutableStateOf(false) }
    val settingsState by viewModel.settingsState.collectAsState()

    Scaffold(
        topBar = {
            AppBar(screen = ScreenType.Settings.screen) {
                navController.navigate(ScreenType.CalculatorMain.route)
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            Box(
                modifier = modifier
                    .padding(horizontal = 10.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(.45f),
                    verticalArrangement = Arrangement.Top
                ) {
                    TextViewWithColorCircle(
                        modifier = Modifier.weight(1f)
                    ) {
                        showPopup = true
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 5.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    TextViewWithSwitch(
                        text = "Rounded button",
                        isChecked = settingsState.isButtonRounded,
                        onCheckedChange = {
                            viewModel.onAction(SettingsAction.ChangeRoundedButton(it))
                        },
                        modifier = Modifier.weight(1f)
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 5.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    TextViewWithSwitch(
                        text = "Haptic feedback",
                        isChecked = settingsState.isHapticFeedbackOn,
                        onCheckedChange = {
                            viewModel.onAction(SettingsAction.ChangeHapticFeedback(it))
                        },
                        modifier = Modifier.weight(1f)
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 5.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    TextViewWithSwitch(
                        text = "Enable double zero",
                        isChecked = settingsState.isDoubleZeroEnabled,
                        onCheckedChange = {
                            viewModel.onAction(SettingsAction.ChangeEnableDoubleZero(it))
                        },
                        modifier = Modifier.weight(1f)
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 5.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    TextViewWithSwitch(
                        text = "Keep device awake",
                        isChecked = settingsState.keepDeviceAwake,
                        onCheckedChange = {
                            viewModel.onAction(SettingsAction.ChangeKeepDeviceAwake(it))
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }

    if (showPopup) {
        ColorThemePopup(onDismiss = { color ->
            showPopup = false
            color?.let {
                viewModel.onAction(SettingsAction.ChangeThemeColor(it))
            }
        })
    }
}