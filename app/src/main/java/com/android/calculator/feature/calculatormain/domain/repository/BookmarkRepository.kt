package com.android.calculator.feature.calculatormain.domain.repository

import com.android.calculator.feature.calculatormain.domain.model.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    suspend fun insertBookmark(bookmark: Bookmark)

    suspend fun deleteBookmarks(bookmarks: List<Bookmark>)

    fun getBookmarks(): Flow<List<Bookmark>>
}