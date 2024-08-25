package com.android.calculator.feature.currencyconverter.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CurrencySwapIcon(onSwapClicked: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSecondary,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        IconButton(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.Center)
                .offset(y = (-25).dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSecondary),
            onClick = onSwapClicked,
        ) {
            Icon(
                imageVector = Icons.Default.SwapVert,
                contentDescription = "Swap Icon",
                modifier = Modifier.size(35.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}