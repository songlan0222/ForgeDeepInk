package com.songlan.deepink.dao

import androidx.room.*
import com.songlan.deepink.model.Chapter
import com.songlan.deepink.model.ChapterContent

@Dao
interface ChapterContentDao {

    @Insert
    fun insertChapterContent(chapter: ChapterContent): Long

    @Update
    fun updateChapterContent(chapter: ChapterContent)

    @Delete
    fun deleteChapterContent(chapter: ChapterContent)

    @Query("select * from ChapterContent where chapterId = :chapterId")
    fun deleteChapterContentWithChapterId(chapterId: Long)

    @Query("delete from ChapterContent where 1 = 1")
    fun deleteAllChapterContent(): Int

    @Query("select * from ChapterContent where chapterId = :chapterId")
    fun loadChapterContent(chapterId: Long)
}