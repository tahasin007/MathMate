package com.android.calculator.feature.calculatormain.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.calculator.feature.calculatormain.domain.model.Bookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks(): Flow<List<Bookmark>>

    @Query("DELETE FROM bookmarks WHERE id IN (:bookmarkIds)")
    suspend fun deleteBookmarks(bookmarkIds: List<Int>)
}