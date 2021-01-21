package com.songlan.deepink.ui.base

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.songlan.deepink.ui.read.ReadBookActivityVM

open class BaseActivity: AppCompatActivity()  {

    /**
     * 加载配置文件中的全部信息
     */
    fun loadPreference(): SharedPreferences {
        var prop = getPreferences(Context.MODE_PRIVATE)
        if(prop.getBoolean("FIRST", true)){
            initPreference()
            prop = getPreferences(Context.MODE_PRIVATE)
        }
        return prop
    }

    /**
     * 对配置文件进行初始化
     */
    private fun initPreference(){
        val map = mutableMapOf(
            "textSize" to 21F,
            "textScaleX" to 1F,
            "lineSpacing" to 1F,
        )
        savePreference(map)
    }

    /**
     * 保存map到配置文件中
     */
    fun savePreference(map: Map<String, Any>){
        val editor = getPreferences(Context.MODE_PRIVATE).edit()
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