package com.android.calculator.feature.calculatormain.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.calculator.feature.calculatormain.domain.model.Calculation
import com.android.calculator.ui.theme.PrimaryLight

@Composable
fun CalculationHistoryPanel(
    calculations: List<Calculation>,
    modifier: Modifier = Modifier
) {
    val displayCalculations = remember(calculations) { calculations.asReversed() }
    val listState = rememberLazyListState()

    LaunchedEffect(displayCalculations.lastOrNull()?.id, displayCalculations.size) {
        if (displayCalculations.isNotEmpty()) {
            listState.animateScrollToItem(displayCalculations.lastIndex)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.18f)
            .padding(horizontal = 5.dp)
            .background(
                color = MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            if (calculations.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "No calculations yet",
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.75f),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                return@Column
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                itemsIndexed(displayCalculations, key = { _, item -> item.id }) { index, item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                val visibleItemInfo = listState.layoutInfo.visibleItemsInfo
                                    .firstOrNull { it.index == index }
                                val itemScale = if (visibleItemInfo == null) {
                                    0.96f
                                } else {
                                    val viewportEnd = listState.layoutInfo.viewportEndOffset.toFloat()
                                    val itemBottom = (visibleItemInfo.offset + visibleItemInfo.size).toFloat()
                                    val distanceFromBottom = (viewportEnd - itemBottom).coerceAtLeast(0f)
                                    // Bottom-most visible item scales to 1f; items above gradually shrink.
                                    (1f - (distanceFromBottom / 700f)).coerceIn(0.92f, 1f)
                                }

                                scaleX = itemScale
                                scaleY = itemScale
                                // Anchor scaling to the right edge so all rows align consistently.
                                transformOrigin = TransformOrigin(1f, 0.5f)
                            }
                    ) {
                        Text(
                            text = item.expression,
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1,
                            textAlign = TextAlign.End,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "= ${item.result}",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W400,
                            color = PrimaryLight
                        )
                    }
                }
            }
        }
    }
}

