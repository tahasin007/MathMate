package com.android.calculator.feature.calculatormain.domain.usecase

import com.android.calculator.feature.calculatormain.domain.usecase.bookmark.DeleteSelectedBookmarksUseCase
import com.android.calculator.feature.calculatormain.domain.usecase.bookmark.GetBookmarksUseCase
import com.android.calculator.feature.calculatormain.domain.usecase.bookmark.InsertBookmarkUseCase

data class BookmarkUseCases(
    val insertBookmark: InsertBookmarkUseCase,
    val getBookmarks: GetBookmarksUseCase,
    val deleteSelectedBookmarks: DeleteSelectedBookmarksUseCase
)