package com.android.calculator.feature.settings.presentaiton.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.android.calculator.ui.theme.ColorBlue
import com.android.calculator.ui.theme.ColorGreen
import com.android.calculator.ui.theme.ColorOrange
import com.android.calculator.ui.theme.ColorRed
import com.android.calculator.ui.theme.ColorSyeBlue
import com.android.calculator.ui.theme.ColorYellow

@Composable
fun ColorThemePopup(
    colors: List<Color> = listOf(
        ColorRed,
        ColorBlue,
        ColorGreen,
        ColorYellow,
        ColorOrange,
        ColorSyeBlue
    ),
    onDismiss: (Int?) -> Unit
) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { onDismiss(null) }
    ) {
        Card(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            elevation = CardDefaults.cardElevation(2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(color)
                            .clickable { onDismiss(color.toArgb()) }
                    )
                }
            }
        }
    }
}