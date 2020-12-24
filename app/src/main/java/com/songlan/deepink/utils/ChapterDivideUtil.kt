package com.songlan.deepink.utils

import android.net.Uri
import android.util.Log
import androidx.core.content.contentValuesOf
import androidx.documentfile.provider.DocumentFile
import com.songlan.deepink.MyApplication.Companion.context
import java.io.*
import java.net.URI

object ChapterDivideUtil {

    // 遍历小说，获取章节名称
    fun getChapterTitlesFromTxt(documentFileUri: Uri): MutableList<String> {
        val documentInputStream = context.contentResolver.openInputStream(documentFileUri)
        val titleList = mutableListOf<String>()
        try {
            val reader = BufferedReader(InputStreamReader(documentInputStream))
            reader.use {
                reader.forEachLine { lineContent ->
                    val regex = Regex("^.*第([0-9]{1,5}|[一二三四五六七八九十百千万亿]{1,5})[章回节部集卷].{0,24}")
                    val matchTitleList = regex.findAll(lineContent)
                    matchTitleList.forEach {
                        Log.d("MainTest", it.value)
                        titleList.add(it.value.trim())
                    }
                }
            }
            if (titleList.size == 0) {
                val reader = BufferedReader(InputStreamReader(documentInputStream, "GB2312"))
                reader.use {
                    reader.forEachLine { lineContent ->
                        val regex = Regex("^.*第([0-9]{1,5}|[一二三四五六七八九十百千万亿]{1,5})[章回节部集卷].{0,24}")
                        val matchTitleList = regex.findAll(lineContent)
                        matchTitleList.forEach {
                            Log.d("MainTest", it.value)
                            titleList.add(it.value.trim())
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return titleList
    }


}