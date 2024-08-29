package com.android.calculator.feature.calculatormain.presentation.bookmark.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.calculator.feature.calculatormain.domain.model.Bookmark
import com.android.calculator.utils.CommonUtils.formatDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkItem(
    bookmark: Bookmark,
    selectionMode: Boolean,
    selectedItems: MutableList<Bookmark>,
    onLongPress: () -> Unit
) {
    val isSelected = bookmark in selectedItems
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondary,
                        shape = RoundedCornerShape(8.dp)
                    )
                } else {
                    Modifier
                }
            )
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    if (selectionMode) {
                        if (selectedItems.contains(bookmark)) {
                            selectedItems.remove(bookmark)
                        } else {
                            selectedItems.add(bookmark)
                        }
                    }
                },
                onLongClick = {
                    onLongPress()
                    selectedItems.add(bookmark)
                },
            )
            .indication(
                interactionSource,
                rememberRipple(
                    bounded = true,
                    color = MaterialTheme.colorScheme.onSecondary.copy(0.1f),
                    radius = 16.dp
                )
            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.25f))
                .padding(vertical = 5.dp, horizontal = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = bookmark.name,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = buildAnnotatedString {
                    bookmark.expression.forEach { char ->
                        val color =
                            if (char.isDigit() || char == '.') MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSecondary
                        withStyle(style = SpanStyle(color = color)) {
                            append(char)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                fontSize = 14.sp,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = bookmark.result,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.W300,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = formatDate(bookmark.date),
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.W200,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}