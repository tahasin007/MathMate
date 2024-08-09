package com.android.calculator.ui.common.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberCounter(
    modifier: Modifier,
    initialValue: Int = 1,
    minValue: Int = 0,
    maxValue: Int = 99,
    onValueChange: (Int) -> Unit
) {
    var counterValue by remember { mutableIntStateOf(initialValue) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                if (counterValue >= minValue + 1) {
                    counterValue -= 1
                    onValueChange(counterValue)
                }
            },
            modifier = Modifier
                .alpha(if (counterValue > minValue) 1f else 0.25f)
                .weight(.2f)
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrement",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }

        AnimatedContent(
            modifier = Modifier
                .weight(.6f)
                .wrapContentWidth(Alignment.CenterHorizontally),
            targetState = counterValue.toString(),
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { -it } togetherWith slideOutVertically { it }
                } else {
                    slideInVertically { it } togetherWith slideOutVertically { -it }
                }
            }, label = ""
        ) { counterValue ->
            Text(
                text = counterValue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

        IconButton(
            onClick = {
                if (counterValue < maxValue - 1) {
                    counterValue += 1
                    onValueChange(counterValue)
                }
            },
            modifier = Modifier
                .alpha(if (counterValue <= maxValue) 1f else 0.25f)
                .weight(.2f)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increment",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}