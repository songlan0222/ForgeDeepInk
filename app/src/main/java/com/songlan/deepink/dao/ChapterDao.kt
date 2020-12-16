package com.songlan.deepink.dao

import androidx.room.*
import com.songlan.deepink.model.Chapter

@Dao
interface ChapterDao {

    @Insert
    fun insertChapter(chapter: Chapter): Long

    @Update
    fun updateChapter(chapter: Chapter)

    @Delete
    fun deleteChapter(chapter: Chapter)

    @Query("delete from Chapter where bookId = :bookId")
    fun deleteChapterWithBookId(bookId: Long): Int

    @Query("delete from Chapter where 1 = 1 ")
    fun deleteAllChapter(): Int

    @Query("select * from chapter where chapterId = :chapterId")
    fun loadChapter(chapterId: Long): Chapter

    @Query("select * from Chapter where bookId = :bookId")
    fun loadChapterWithBookId(bookId: Long): List<Chapter>
}