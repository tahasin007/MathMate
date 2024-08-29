package com.android.calculator.feature.calculatormain.presentation.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.calculator.R
import com.android.calculator.actions.CalculatorAction
import com.android.calculator.ui.shared.components.ConverterIconView
import com.android.calculator.utils.ScreenType

@Composable
fun MenuSavedItem(
    onAction: (CalculatorAction) -> Unit,
    onNavigate: (screen: String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Saved Items",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
    Spacer(modifier = Modifier.height(25.dp))

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ConverterIconView(
            R.drawable.ic_history,
            ScreenType.History,
            Modifier.weight(1f)
        ) {
            onNavigate.invoke(ScreenType.History.route)
            onAction(CalculatorAction.ConverterMenuVisibility(false))
        }

        ConverterIconView(
            R.drawable.ic_bookmark,
            ScreenType.Bookmark,
            Modifier.weight(1f)
        ) {
            onNavigate.invoke(ScreenType.Bookmark.route)
            onAction(CalculatorAction.ConverterMenuVisibility(false))
        }
    }
}