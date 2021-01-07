package com.songlan.deepink.repository

import android.net.Uri
import androidx.lifecycle.liveData
import com.songlan.deepink.model.Chapter
import com.songlan.deepink.utils.ChapterDivideUtil
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

object ChapterRepository {

    fun getChapterTitlesFromTxt(uri: Uri) = fire(Dispatchers.IO) {
        val titleList = ChapterDivideUtil.getChapterTitlesFromTxt(uri)
        Result.success(titleList)
    }

    fun getChapterContent(chapter: Chapter) = fire(Dispatchers.IO) {
        val content = ChapterDivideUtil.getChapterContent(chapter)
        Result.success(content)
    }

    // 对获取liveData进行简化
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            var result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}