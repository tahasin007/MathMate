package com.android.calculator.feature.calculator.main.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.calculator.R
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.CalculatorAction
import com.android.calculator.feature.calculator.main.presentation.CalculatorMainState
import com.android.calculator.utils.ScreenType

@Composable
fun ActionIconRow(
    state: CalculatorMainState,
    onAction: (BaseAction) -> Unit,
    isDarkTheme: Boolean = false,
    onThemeUpdated: (() -> Unit)? = null,
    onNavigate: ((screen: String) -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .weight(1f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = false,
                        radius = 30.dp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    onNavigate?.invoke(ScreenType.History.route)
                },
            painter = painterResource(id = R.drawable.ic_history),
            contentDescription = "Delete",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_convert),
            contentDescription = "Delete",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary),
            modifier = Modifier
                .weight(1f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = false,
                        radius = 30.dp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    onAction(CalculatorAction.BottomSheetVisibility(state.isBottomSheetOpen.not()))
                }
        )

        ThemeSwitch(isDarkTheme = isDarkTheme) {
            onThemeUpdated?.invoke()
        }

        Image(
            modifier = Modifier
                .weight(1f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = false,
                        radius = 30.dp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    onNavigate?.invoke(ScreenType.Settings.route)
                },
            painter = painterResource(id = R.drawable.ic_settings),
            contentDescription = "Delete",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = "Delete",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary),
            modifier = Modifier
                .weight(1f)
                .then(
                    if (state.expression.isEmpty() || state.expression == "0") {
                        Modifier.alpha(0.5f)
                    } else {
                        Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                bounded = false,
                                radius = 30.dp,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        ) {
                            onAction(BaseAction.Delete)
                        }
                    }
                )
        )
    }
}