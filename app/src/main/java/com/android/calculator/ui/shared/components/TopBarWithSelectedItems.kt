package com.android.calculator.ui.shared.components

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.android.calculator.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithSelectedItems(
    selectedItemSize: Int,
    totalItemSize: Int,
    onDeleteBtnClick: () -> Unit,
    onNavigationBtnClick: () -> Unit,
) {
    TopAppBar(
        title = { Text("$selectedItemSize Selected") },
        actions = {
            IconButton(onClick = onDeleteBtnClick) {
                Image(
                    painter = painterResource(id = R.drawable.ic_multiselect),
                    contentDescription = "Select All",
                    colorFilter = if (selectedItemSize == totalItemSize) ColorFilter.tint(
                        MaterialTheme.colorScheme.onSecondary
                    )
                    else ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigationBtnClick) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Cancel",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}