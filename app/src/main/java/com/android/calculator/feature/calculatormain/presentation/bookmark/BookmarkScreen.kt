package com.android.calculator.feature.calculatormain.presentation.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.calculator.feature.calculatormain.domain.model.Bookmark
import com.android.calculator.feature.calculatormain.presentation.bookmark.component.BookmarkItem
import com.android.calculator.ui.shared.components.AppBar
import com.android.calculator.ui.shared.components.DeleteItemBottomBar
import com.android.calculator.ui.shared.components.EmptyItemsView
import com.android.calculator.ui.shared.components.TopBarWithSelectedItems
import com.android.calculator.utils.ScreenType

@Composable
fun BookmarkScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val bookmarks = viewModel.bookmarks

    val (selectionMode, setSelectionMode) = remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<Bookmark>() }
    val isAllItemsSelected = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            if (selectionMode) {
                TopBarWithSelectedItems(
                    selectedItemSize = selectedItems.size,
                    totalItemSize = bookmarks.value.size,
                    onDeleteBtnClick = {
                        isAllItemsSelected.value = !isAllItemsSelected.value
                        selectedItems.clear()
                        if (isAllItemsSelected.value) {
                            selectedItems.addAll(bookmarks.value)
                        }
                    },
                    onNavigationBtnClick = {
                        setSelectionMode(false)
                        selectedItems.clear()
                    },
                )
            } else {
                AppBar(screen = ScreenType.Bookmark.screen) {
                    navController.navigate(ScreenType.CalculatorMain.route)
                }
            }
        },
        bottomBar = {
            DeleteItemBottomBar(
                selectionMode = selectionMode,
                onDelete = {
                    viewModel.deleteSelectedBookmarks(selectedItems)
                    setSelectionMode(false)
                    selectedItems.clear()
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (bookmarks.value.isEmpty()) {
                EmptyItemsView(modifier = modifier)
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(bookmarks.value.size) { idx ->
                        BookmarkItem(
                            bookmark = bookmarks.value[idx],
                            selectionMode = selectionMode,
                            selectedItems = selectedItems,
                            onLongPress = {
                                setSelectionMode(true)
                            }
                        )
                    }
                }
            }
        }
    }
}