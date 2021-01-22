package com.songlan.deepink.utils

import android.net.Uri
import android.util.Log
import com.songlan.deepink.MyApplication.Companion.context
import com.songlan.deepink.model.data.Book
import com.songlan.deepink.model.data.Chapter
import com.songlan.deepink.ui.local.AddLocalBookEditActivityVM
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder

/**
 * 章节工具
 */
object ChapterUtil {

    private val CHAPTER_SPLIT_REGEX =
        Regex("^.*第([0-9]{1,5}|[一二三四五六七八九十百千万亿]{1,5})[章回节部集卷](\\s.{0,24}|$)")

    // 遍历小说，获取章节名称
    fun getChapterTitlesFromTxt(documentFileUri: Uri): MutableList<String> {
        val documentInputStream = context.contentResolver.openInputStream(documentFileUri)
        val codeType = documentInputStream?.let { getFileCharsetName(documentInputStream) }
        Log.v("MainTest", "codeType: $codeType")

        val titleList = mutableListOf<String>()
        try {
            val reader = BufferedReader(InputStreamReader(documentInputStream, codeType))
            reader.use {
                reader.forEachLine { lineContent ->
                    val matchTitleList = CHAPTER_SPLIT_REGEX.findAll(lineContent)
                    matchTitleList.forEach {
                        // Log.d("MainTest", it.value)
                        titleList.add(it.value.trim())
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            Log.d("MainTest", titleList.size.toString())
        }
        return titleList
    }

    // 导入小说，进行章节切分
    fun getChaptersFromTxt(vm: AddLocalBookEditActivityVM, documentFileUri: Uri, book: Book) {
        val documentInputStream = context.contentResolver.openInputStream(documentFileUri)
        val codeType = documentInputStream?.let {
            getFileCharsetName(it)
        }

        try {
            val reader = BufferedReader(InputStreamReader(documentInputStream, codeType))
            mkBookDir()
            reader.use {
                var index = 0
                var output: FileOutputStream? = null
                var writer: BufferedWriter? = null
                var chapter: Chapter? = null
                reader.forEachLine { lineContent ->
                    val isMatches = CHAPTER_SPLIT_REGEX.matches(lineContent)
                    if (isMatches) {
                        writer?.close()
                        output?.close()
                        val folderPath = mkChapterDir(book.bookName)
                        index++

                        // 创建章节文件
                        val file = File("${folderPath}/${index}")
                        output = FileOutputStream(file)

                        chapter?.let {
                            LogUtils.v(msg = "保存当前章节：${it.chapterName} 书籍Id为：${it.bookId}")
                            vm.insertChapter(it)
                        }
                        chapter =
                            Chapter(lineContent, "${folderPath}/$index", book.bookId)
                        // writer?.close()
                        writer = BufferedWriter(OutputStreamWriter(output))
                        writer?.write(lineContent)
                        writer?.newLine()
                    } else {
                        // 判断是否为章节前的垃圾信息，如果是的话，还没获取到章节标题
                        // 此时，先不保存数据
                        writer?.write(lineContent)
                        writer?.newLine()
                    }
                }
                // 如果output和writer不为空，则关闭
                chapter?.let {
                    vm.insertChapter(it)
                }
                writer?.close()
                output?.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // 获取章节内容
    fun getChapterContent(chapter: Chapter): StringBuilder {

        val contentPath = chapter.contentPath
        LogUtils.v(msg = "当前章节路径: $contentPath")
        LogUtils.v(msg = "章节文件是否存在：${File(contentPath).exists()}")
        val content = StringBuilder()
        try {
            val file = File(contentPath)
            val input = file.inputStream()
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine {
                    content.append(it).append("\n")
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return content


    }


    // 创建book文件夹，存放数据
    private fun mkBookDir() {
        val folder = File("${context.filesDir.absolutePath}/book")
        if (!folder.exists()) {
            folder.mkdirs()
        }
    }

    // 创建章节的存放目录
    private fun mkChapterDir(bookName: String): String {
        val folder = File("${context.filesDir.absolutePath}/book/$bookName")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        return "${context.filesDir.absolutePath}/book/$bookName"
    }

    // 获取Text的编码方式
    private fun getFileCharsetName(inputStream: InputStream): String? {
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

        return charsetName
    }

}