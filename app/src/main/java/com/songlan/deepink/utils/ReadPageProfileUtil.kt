package com.songlan.deepink.utils

import android.content.Context
import com.songlan.deepink.MyApplication
import java.io.File

object ReadPageProfileUtil {
    private const val READ_PAGE_PROFILE = "READ_PAGE_PROFILE"
    const val FONT_SIZE = "FONT_SIZE"
    const val FONT_MARGIN = "FONT_MARGIN"
    const val LINE_MARGIN = "LINE_MARGIN"
    const val PARAGRAPH_MARGIN = "PARAGRAPH_MARGIN"

    /**
     * 初始化ReadPage的基本信息
     */
    private fun initReadPageProfile(){
        val editor = MyApplication.context.getSharedPreferences(READ_PAGE_PROFILE, Context.MODE_PRIVATE).edit()
        editor.putFloat(FONT_SIZE, 21F)
        editor.putFloat(FONT_MARGIN, 0F)
        editor.putFloat(LINE_MARGIN, 1.5F)
        editor.putFloat(PARAGRAPH_MARGIN, 0F)
        editor.apply()
    }

    /**
     * 获取ReadPage的配置信息
     */
    fun loadReadPageProfile(): MutableMap<String, Float> {
        LogUtils.v(msg="加载ReadPage配置信息中，in ReadPageProfileUtil")
        val prefs = MyApplication.context.getSharedPreferences(READ_PAGE_PROFILE, Context.MODE_PRIVATE)
        val resultMap = mutableMapOf<String, Float>()
        resultMap[FONT_SIZE] = prefs.getFloat(FONT_SIZE, 14F)
        resultMap[FONT_MARGIN] = prefs.getFloat(FONT_MARGIN, 0F)
        resultMap[LINE_MARGIN] = prefs.getFloat(LINE_MARGIN, 0F)
        resultMap[PARAGRAPH_MARGIN] = prefs.getFloat(PARAGRAPH_MARGIN, 0F)
        return resultMap
    }

    /**
     * 保存ReadPage的配置信息
     */
    fun saveReadPageProfile(resultMap: Map<String, Float>){
        val file = File("shared_prefs/$READ_PAGE_PROFILE")
        if(!file.exists()){
            initReadPageProfile()
        }
        val editor = MyApplication.context.getSharedPreferences(READ_PAGE_PROFILE, Context.MODE_PRIVATE).edit()
        resultMap[FONT_SIZE]?.let { editor.putFloat(FONT_SIZE, it) }
        resultMap[FONT_MARGIN]?.let { editor.putFloat(FONT_MARGIN, it) }
        resultMap[LINE_MARGIN]?.let { editor.putFloat(LINE_MARGIN, it) }
        resultMap[PARAGRAPH_MARGIN]?.let { editor.putFloat(PARAGRAPH_MARGIN, it) }
        editor.apply()
    }
}