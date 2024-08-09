package com.android.calculator.feature.settings.presentaiton

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.calculator.feature.settings.presentaiton.component.TextViewWithSwitch
import com.android.calculator.feature.settings.presentaiton.component.ColorThemePopup
import com.android.calculator.ui.common.components.AppBar
import com.android.calculator.utils.ScreenType

@Composable
fun SettingsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var showPopup by remember { mutableStateOf(false) }
    var isSwitchOn by remember { mutableStateOf(false) }
    var shouldVibrate by remember { mutableStateOf(false) }

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
            Column(
                modifier = modifier.padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                        .clickable {
                            showPopup = true
                        },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Change Theme",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onSecondary)
                    )
                }

                TextViewWithSwitch(
                    text = "Rounded button",
                    isChecked = isSwitchOn,
                    onCheckedChange = { isSwitchOn = it }
                )

                TextViewWithSwitch(
                    text = "Vibrate on key press",
                    isChecked = shouldVibrate,
                    onCheckedChange = { shouldVibrate = it }
                )

                TextViewWithSwitch(
                    text = "Enable double zero",
                    isChecked = shouldVibrate,
                    onCheckedChange = { shouldVibrate = it }
                )
            }
        }
    }

    if (showPopup) {
        ColorThemePopup(onDismiss = { showPopup = false })
    }
}