package com.songlan.deepink.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.pow

object FormatUtil {

    // 时间格式化工具
    fun getFormatFileLastModified(time: Long): String {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val instant = Instant.ofEpochMilli(time)
            val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            val format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
            format.format(date).toString()
        } else {
            // 这部分没用放在android O以下测试
            val date = Date(time)
            val format = SimpleDateFormat("yyyy/MM/dd HH:mm")
            format.format(date)
        }
    }

    // 文件大小格式化方法
    fun getFormatFileSize(length: Long): String {
        LogUtil.d("MainTest", "文件大小为：$length")
        val arr = arrayOf("Bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB")
        var index = 0
        var value = length.toDouble()
        while (value > 1024) {
            value /= 1024
            index++
        }
        //var size = length / 1024.0.pow(index.toDouble())
        val sizeStr = DecimalFormat("#.0").format(value)
        return "$sizeStr ${arr[index]}"

    }
}