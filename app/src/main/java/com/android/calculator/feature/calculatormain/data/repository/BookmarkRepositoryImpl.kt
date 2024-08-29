package com.android.calculator.feature.calculatormain.data.repository

import com.android.calculator.feature.calculatormain.data.source.BookmarkDao
import com.android.calculator.feature.calculatormain.domain.model.Bookmark
import com.android.calculator.feature.calculatormain.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class BookmarkRepositoryImpl(private val dao: BookmarkDao) : BookmarkRepository {
    override suspend fun insertBookmark(bookmark: Bookmark) {
        dao.insertBookmark(bookmark)
    }

    override suspend fun deleteBookmarks(bookmarks: List<Bookmark>) {
        dao.deleteBookmarks(bookmarks.map { it.id })
    }

    override fun getBookmarks(): Flow<List<Bookmark>> {
        return dao.getAllBookmarks()
    }

}