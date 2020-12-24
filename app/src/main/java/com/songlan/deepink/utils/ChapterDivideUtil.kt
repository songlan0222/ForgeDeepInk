package com.songlan.deepink.utils

import android.net.Uri
import androidx.core.content.contentValuesOf
import androidx.documentfile.provider.DocumentFile
import com.songlan.deepink.MyApplication.Companion.context
import java.io.*
import java.net.URI

object ChapterDivideUtil {

    // 遍历小说，获取章节名称
    fun getTxtChapterTitle(documentFileUri: Uri): MutableList<String> {
//        val cursor = context.contentResolver.query(documentFileUri, null, null, null, null)?.apply {
//            while(moveToNext()){
//                columnNames.forEach {
//                    LogUtil.v(msg="$it")
//                }
//            }
//            close()
//        }

        val documentInputStream = context.contentResolver.openInputStream(documentFileUri)
        //val document = File(URI(documentFileUri.path))
        val titleList = mutableListOf<String>()
        try {
            //val documentPath = document.path
            //val inputStream = context.openFileInput(documentPath)
            val reader = BufferedReader(InputStreamReader(documentInputStream,"GB2312"))

            reader.use {
                reader.forEachLine { lineContent ->
                    // LogUtil.d("MainTest", lineContent)
                    val regex = Regex("^.*第([0-9]{1,5}|[一二三四五六七八九十百千万亿]{1,5})[章回节部集卷].{0,24}")
                    val matchTitleList = regex.findAll(lineContent)
                    matchTitleList.forEach {
                        LogUtil.d("MainTest", it.value)
                        titleList.add(it.value.trim())
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return titleList

    }


}