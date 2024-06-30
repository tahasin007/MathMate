package com.android.calculator.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.android.calculator.actions.BaseAction
import com.android.calculator.state.DiscountState
import com.android.calculator.state.ScreenType
import com.android.calculator.ui.components.CalculatorGridSimple
import com.android.calculator.ui.factory.ButtonFactory

@Composable
fun Discount(
    state: DiscountState,
    buttonSpacing: Dp,
    modifier: Modifier,
    onAction: (BaseAction) -> Unit
) {
    Box(modifier = modifier) {
        val buttons = ButtonFactory()
        CalculatorGridSimple(
            buttons = buttons.getButtons(ScreenType.DISCOUNT),
            onAction = onAction,
            buttonSpacing = buttonSpacing
        )
    }
}