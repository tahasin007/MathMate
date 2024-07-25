package com.android.calculator.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.android.calculator.R
import com.android.calculator.actions.BaseAction
import com.android.calculator.actions.CalculatorAction

@Composable
fun ActionIconRow(onAction: (BaseAction) -> Unit) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = "Delete",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_scale),
            contentDescription = "Delete",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            modifier = Modifier.clickable {
                onAction(CalculatorAction.BottomSheetVisibility)
            }
        )
        Image(
            painter = painterResource(id = R.drawable.ic_settings),
            contentDescription = "Delete",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
        )
        Image(
            painter = painterResource(id = R.drawable.cal_delete),
            contentDescription = "Delete",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
        )
    }
}