package com.android.calculator.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SliderInfoBox(label: String) {
    Box(modifier = Modifier.size(100.dp)) {
        // Rectangle
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSecondary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                maxLines = 1
            )
        }

        // Upside-down Triangle
        Box(
            modifier = Modifier
                .size(20.dp)
                .offset(y = 37.dp, x = 40.dp)
                .clip(UpsideDownTriangleShape)
                .background(MaterialTheme.colorScheme.onSecondary)
        )
    }
}

val UpsideDownTriangleShape = GenericShape { size, _ ->
    val extendedWidth = size.width * 2f

    moveTo(size.width / 2f, size.height)  // Start from the bottom center of the rectangle
    lineTo((size.width / 2f) - (extendedWidth / 2f), 0f) // Line to the left bottom corner of the triangle
    lineTo((size.width / 2f) + (extendedWidth / 2f), 0f) // Line to the right bottom corner of the triangle
    close()  // Close the path to form the triangle
}