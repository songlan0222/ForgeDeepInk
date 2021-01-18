package com.songlan.deepink.utils

import android.content.Context
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*

object PropertiesUtil {
    fun loadConfig(context: Context, file: String): Properties{
        val properties = Properties()
        try{
            val s = FileInputStream(file)
            properties.load(s)
        } catch (e: Exception){
            e.printStackTrace()
        }
        return properties
    }

    fun saveConfig(context: Context, file: String, properties: Properties){
        try{
            val s = FileOutputStream(file, false)
            properties.store(s, "")
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}