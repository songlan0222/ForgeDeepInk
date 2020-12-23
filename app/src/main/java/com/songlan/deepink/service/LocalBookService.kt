package com.songlan.deepink.service

import com.songlan.deepink.MyApplication.Companion.context
import com.songlan.deepink.model.Book
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class LocalBookService {

    // 待修改，应该划分章节
    fun loadLocalBook(bookPath: String): String {
        val content = StringBuilder()
        try {
            val input = context.openFileInput("data")
            val reader = BufferedReader(input as InputStreamReader)
            reader.use {
                reader.forEachLine {
                    content.append(it)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return content.toString()
    }
}