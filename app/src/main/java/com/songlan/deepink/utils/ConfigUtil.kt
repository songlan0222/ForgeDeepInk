package com.songlan.deepink.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.songlan.deepink.MyApplication.Companion.context

/**
 * 配置文件工具
 */
object ConfigUtil {

    /**
     * 加载配置文件， 并返回map
     */
    fun loadPreference(configName: String): SharedPreferences =
        context.getSharedPreferences(configName, Context.MODE_PRIVATE)

    /**
     * 将map保存到配置文件中
     */
    fun savePreference(configName: String, map: Map<String, Any>) {
        val editor = context.getSharedPreferences(configName, Context.MODE_PRIVATE).edit()
        map.forEach {
            when (it.value) {
                is Int -> {
                    editor.putInt(it.key, it.value as Int)
                }
                is Float -> {
                    editor.putFloat(it.key, it.value as Float)
                }
                is Boolean -> {
                    editor.putBoolean(it.key, it.value as Boolean)
                }
                is String -> {
                    editor.putString(it.key, it.value as String)
                }
            }
        }
        editor.apply()
    }
}