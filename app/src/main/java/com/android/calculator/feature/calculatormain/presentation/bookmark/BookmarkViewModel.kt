package com.android.calculator.feature.calculatormain.presentation.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.feature.calculatormain.domain.model.Bookmark
import com.android.calculator.feature.calculatormain.domain.usecase.BookmarkUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkUseCases: BookmarkUseCases
) : ViewModel() {

    private val _bookmarks = mutableStateOf<List<Bookmark>>(emptyList())
    val bookmarks: State<List<Bookmark>> = _bookmarks

    private var bookmarkJob: Job? = null

    init {
        getBookmarks()
    }

    private fun getBookmarks() {
        bookmarkJob?.cancel()
        bookmarkJob = bookmarkUseCases.getBookmarks()
            .onEach { bookmarks ->
                _bookmarks.value = bookmarks
            }
            .launchIn(viewModelScope)
    }

    fun deleteSelectedBookmarks(bookmarks: List<Bookmark>) {
        viewModelScope.launch {
            bookmarkUseCases.deleteSelectedBookmarks(bookmarks)
        }
    }
}