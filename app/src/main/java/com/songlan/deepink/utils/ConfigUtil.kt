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
//    fun loadActivityPreference(): MutableMap<String, *>? {
//        val prop = context.getPreferences(Context.MODE_PRIVATE)
//        val all = prop.all
//        return all
//    }

    /**
     * 将map保存到配置文件中
     */
//    fun saveActivityPreference(activity: Activity, map: Map<String, Any>) {
//        val editor = activity.getPreferences(Context.MODE_PRIVATE).edit()
//        map.forEach {
//            when (it.value) {
//                is Int -> {
//                    editor.putInt(it.key, it.value as Int)
//                }
//                is Float -> {
//                    editor.putFloat(it.key, it.value as Float)
//                }
//                is Boolean -> {
//                    editor.putBoolean(it.key, it.value as Boolean)
//                }
//                is String -> {
//                    editor.putString(it.key, it.value as String)
//                }
//            }
//        }
//        editor.apply()
//    }
}