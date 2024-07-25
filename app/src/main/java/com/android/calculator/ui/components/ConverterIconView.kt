package com.android.calculator.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.android.calculator.state.ScreenType

@Composable
fun ConverterIconView(
    drawableId: Int,
    screen: ScreenType,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.clickable {
            onClick.invoke()
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = screen.route,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
        )
        Text(text = screen.route)
    }
}