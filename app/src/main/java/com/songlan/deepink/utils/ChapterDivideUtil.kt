package com.songlan.deepink.utils

import android.net.Uri
import android.util.Log
import com.songlan.deepink.MyApplication.Companion.context
import java.io.*


object ChapterDivideUtil {

    // 遍历小说，获取章节名称
    fun getChapterTitlesFromTxt(documentFileUri: Uri): MutableList<String> {

//        val path = UriTofilePath.getFilePathByUri(context, documentFileUri)
//        val file = File(path)
//        val encode = getFileIncode(file)
//        Log.v("MainTest", "CodeType: $encode")


        val documentInputStream = context.contentResolver.openInputStream(documentFileUri)
        val codeType = documentInputStream?.let { getFileCharsetName(documentInputStream) }
        Log.v("MainTest", "codeType: $codeType")

        val regex = Regex("^.*第([0-9]{1,5}|[一二三四五六七八九十百千万亿]{1,5})[章回节部集卷].{0,24}")
        val titleList = mutableListOf<String>()
        try {
            val reader = BufferedReader(InputStreamReader(documentInputStream, codeType))
            reader.use {
                reader.forEachLine { lineContent ->
                    val matchTitleList = regex.findAll(lineContent)
                    matchTitleList.forEach {
                        Log.d("MainTest", it.value)
                        titleList.add(it.value.trim())
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return titleList
    }

    private fun getFileCharsetName(inputStream: InputStream): String? {
//        val inputStream: InputStream = FileInputStream(fileName)
        val head = ByteArray(3)
        inputStream.read(head)
        var charsetName = "GBK" //或GB2312，即ANSI
        if (head[0].toInt() and 0xff == 0xFF &&
            head[1].toInt() and 0xff == 0xFE
        ) // 0xFFFE
            charsetName = "UTF-16"
        else if (head[0].toInt() and 0xff == 0xFE &&
            head[1].toInt() and 0xff == 0xFF
        ) // 0xFEFF
            charsetName = "Unicode" //包含两种编码格式：UCS2-Big-Endian和UCS2-Little-Endian
        else if (head[0].toInt() and 0xff == 0xE5 &&
            head[1].toInt() and 0xff == 0x9B &&
            head[2].toInt() and 0xff == 0x9E
        ) // 0xE59B9E
            charsetName = "UTF-8" //UTF-8(不含BOM)
        else if (head[0].toInt() and 0xff == 0xEF &&
            head[1].toInt() and 0xff == 0xBB &&
            head[2].toInt() and 0xff == 0xBF
        )// 0xEFBBBF
            charsetName = "UTF-8" //UTF-8-BOM
        // inputStream.close()

        //System.out.println(code);
        return charsetName
    }

}