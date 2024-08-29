package com.android.calculator.feature.calculatormain.domain.usecase.bookmark

import com.android.calculator.feature.calculatormain.domain.repository.BookmarkRepository

class GetBookmarksUseCase(private val repository: BookmarkRepository) {
    operator fun invoke() = repository.getBookmarks()
}