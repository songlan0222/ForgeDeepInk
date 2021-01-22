package com.songlan.deepink.repository

import android.net.Uri
import com.songlan.deepink.model.data.Chapter
import com.songlan.deepink.utils.ChapterUtil
import com.songlan.deepink.utils.fire
import kotlinx.coroutines.Dispatchers

object ChapterRepository {

    /**
     * 从txt文件中，获取章节标题
     */
    fun getChapterTitlesFromTxt(uri: Uri) = fire(Dispatchers.IO) {
        val titleList = ChapterUtil.getChapterTitlesFromTxt(uri)
        Result.success(titleList)
    }

    /**
     * 从txt文件中，切割小说章节，并保存
     */
    fun getChapterContentFromTxt(chapter: Chapter) = fire(Dispatchers.IO) {
        val content = ChapterUtil.getChapterContent(chapter)
        Result.success(content)
    }
}