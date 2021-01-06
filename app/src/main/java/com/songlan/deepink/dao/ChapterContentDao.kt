package com.songlan.deepink.dao

import androidx.room.*
import com.songlan.deepink.model.Chapter
import com.songlan.deepink.model.ChapterContent

@Dao
interface ChapterContentDao {

    @Insert
    suspend fun insertChapterContent(chapter: ChapterContent): Long

    @Update
    suspend fun updateChapterContent(chapter: ChapterContent)

    @Delete
    suspend fun deleteChapterContent(chapter: ChapterContent)

    @Query("select * from ChapterContent where chapterId = :chapterId")
    suspend fun deleteChapterContentWithChapterId(chapterId: Long)

    @Query("delete from ChapterContent where 1 = 1")
    suspend fun deleteAllChapterContent(): Int

    @Query("select * from ChapterContent where chapterId = :chapterId")
    suspend fun loadChapterContent(chapterId: Long)
}