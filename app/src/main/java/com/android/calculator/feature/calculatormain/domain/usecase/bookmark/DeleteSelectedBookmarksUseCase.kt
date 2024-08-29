package com.android.calculator.feature.calculatormain.domain.usecase.bookmark

import com.android.calculator.feature.calculatormain.domain.model.Bookmark
import com.android.calculator.feature.calculatormain.domain.repository.BookmarkRepository

class DeleteSelectedBookmarksUseCase(private val repository: BookmarkRepository) {
    suspend operator fun invoke(bookmarks: List<Bookmark>) {
        repository.deleteBookmarks(bookmarks)
    }
}