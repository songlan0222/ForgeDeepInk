package com.songlan.deepink.utils

import android.net.Uri
import android.util.Log
import com.songlan.deepink.MyApplication.Companion.context
import java.io.*

object ChapterDivideUtil {

    // 遍历小说，获取章节名称
    fun getChapterTitlesFromTxt(documentFileUri: Uri): MutableList<String> {
        val documentInputStream = context.contentResolver.openInputStream(documentFileUri)
        val regex = Regex("^.*第([0-9]{1,5}|[一二三四五六七八九十百千万亿]{1,5})[章回节部集卷].{0,24}")
        val titleList = mutableListOf<String>()
        try {
            val reader = BufferedReader(InputStreamReader(documentInputStream))
            reader.use {
                reader.forEachLine { lineContent ->
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

    fun getFileIncode(file: File): String? {
        if (!file.exists()) {
            System.err.println("getFileIncode: file not exists!")
            return null
        }
        val buf = ByteArray(4096)
        var fis: FileInputStream? = null
        try {
            fis = FileInputStream(file)
            // (1)
            val detector = UniversalDetector(null)

            // (2)
            var nread: Int
            while (fis.read(buf).also { nread = it } > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread)
            }
            // (3)
            detector.dataEnd()

            // (4)
            val encoding: String = detector.getDetectedCharset()
            if (encoding != null) {
                println("Detected encoding = $encoding")
            } else {
                println("No encoding detected.")
            }

            // (5)
            detector.reset()
            fis.close()
            return encoding
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}