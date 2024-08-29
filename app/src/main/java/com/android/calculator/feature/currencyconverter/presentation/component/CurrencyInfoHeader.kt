package com.android.calculator.feature.currencyconverter.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.calculator.feature.currencyconverter.presentation.utils.CurrencyUtils

@Composable
fun CurrencyInfoHeader(
    selectedCurrency: String,
    items: List<String>,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onSelectedUnitChanged: (String) -> Unit
) {
    var selectedItem by remember { mutableStateOf(selectedCurrency) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(0.75f)) {
            Image(
                painter = painterResource(
                    id = CurrencyUtils.getFlagDrawable(selectedCurrency)
                ),
                contentDescription = selectedCurrency,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .padding(top = 2.5.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Text(
                    text = "From",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = .75f)
                )
                Text(
                    text = "$selectedCurrency - ${
                        CurrencyUtils.getCurrencyFullName(selectedCurrency)
                    }",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.height(30.dp),
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Row(
            modifier = Modifier.weight(0.25f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { onExpandChange(!expanded) }) {
                Text(
                    text = "Change",
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown Arrow",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandChange(false) },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 200.dp)
                        .heightIn(max = 500.dp)
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Image(
                                            painter = painterResource(
                                                id = CurrencyUtils.getFlagDrawable(item)
                                            ),
                                            contentDescription = item,
                                            modifier = Modifier
                                                .size(25.dp)
                                                .padding(end = 5.dp)
                                        )
                                        Text(
                                            text = item,
                                            color = if (item == selectedItem) MaterialTheme.colorScheme.onSecondary
                                            else MaterialTheme.colorScheme.onPrimary,
                                        )
                                    }
                                    if (item == selectedItem) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Selected",
                                            tint = MaterialTheme.colorScheme.onSecondary
                                        )
                                    }
                                }
                            },
                            onClick = {
                                selectedItem = item
                                onExpandChange(false)
                                onSelectedUnitChanged(item)
                            },
                            modifier = Modifier
                                .background(
                                    if (item == selectedItem) MaterialTheme.colorScheme.onSecondary.copy(
                                        alpha = .1f
                                    )
                                    else MaterialTheme.colorScheme.primary
                                )
                        )
                    }
                }
            }
        }
    }
}