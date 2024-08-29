package com.android.calculator.feature.calculatormain.domain.usecase.bookmark

import com.android.calculator.feature.calculatormain.domain.model.Bookmark
import com.android.calculator.feature.calculatormain.domain.repository.BookmarkRepository

class InsertBookmarkUseCase(private val repository: BookmarkRepository) {
    suspend operator fun invoke(bookmark: Bookmark) {
        repository.insertBookmark(bookmark)
    }
}