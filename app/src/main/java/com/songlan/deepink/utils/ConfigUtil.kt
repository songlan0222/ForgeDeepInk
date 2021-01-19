package com.songlan.deepink.utils

import android.content.Context
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*

/**
 * 配置文件工具
 */
object ConfigUtil {
    fun loadConfig(context: Context, file: String): Properties{
        val properties = Properties()
        try{
            val s = context.openFileInput(file)
            properties.load(s)
        } catch (e: Exception){
            e.printStackTrace()
        }
        return properties
    }

    fun saveConfig(context: Context, file: String, properties: Properties){
        try{
            val s = context.openFileOutput(file, Context.MODE_PRIVATE)
            properties.store(s, "")
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}