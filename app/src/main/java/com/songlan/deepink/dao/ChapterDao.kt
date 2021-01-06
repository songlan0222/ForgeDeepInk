package com.songlan.deepink.dao

import androidx.room.*
import com.songlan.deepink.model.Chapter

@Dao
interface ChapterDao {

    @Insert
    suspend fun insertChapter(chapter: Chapter): Long

    @Update
    suspend fun updateChapter(chapter: Chapter)

    @Delete
    suspend fun deleteChapter(chapter: Chapter)

    @Query("delete from Chapter where bookId = :bookId")
    suspend fun deleteChapterWithBookId(bookId: Long): Int

    @Query("delete from Chapter where 1 = 1 ")
    suspend fun deleteAllChapter(): Int

    @Query("select * from chapter where chapterId = :chapterId")
    suspend fun loadChapter(chapterId: Long): Chapter

    @Query("select * from Chapter where bookId = :bookId")
    suspend fun loadChapterWithBookId(bookId: Long): List<Chapter>

    @Query("select * from chapter where bookId = :bookId")
    suspend fun loadChaptersWithBookId(bookId: Long): List<Chapter>
}