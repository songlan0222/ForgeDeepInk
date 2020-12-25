package com.songlan.deepink

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.songlan.deepink.MyApplication.Companion.context
import com.songlan.deepink.ui.local.AddLocalBookActivity
import com.songlan.deepink.ui.local.AddLocalBookEditActivity
import com.songlan.deepink.ui.local.RequestStoragePermissive
import com.songlan.deepink.ui.settings.SettingActivity

object AppProfiles {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_profile", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    // 默认选中的书架ID
    const val CHECKED_BOOKSHELF_ID = "CHECKED_BOOKSHELF_ID"
    private const val DEFAULT_BOOKSHELF_ID = 1L
    fun getCheckedBookshelfIdFromProfile(): Long {
        return prefs.getLong(CHECKED_BOOKSHELF_ID, DEFAULT_BOOKSHELF_ID)
    }

    // 配置界面参数
    fun getOtherUIParams() {
    }

    // 设置界面的默认参数
    const val CHECK_UPDATE = "CHECK_UPDATE"
    const val SIGN_NOTIFY = "SIGN_NOTIFY"
    const val MIDDLE_FONT_SIZE = "MIDDLE_FONT_SIZE"
    const val FOLLOW_SYSTEM_THEME = "FOLLOW_SYSTEM_THEME"
    const val CHASING_MODE = "CHASING_MODE"
    fun jumpToSettingActivity(activity: AppCompatActivity) {
        val intent = Intent(activity, SettingActivity::class.java)
        val checkUpdate = prefs.getInt(CHECK_UPDATE, 0)
        val signNotify = prefs.getBoolean(SIGN_NOTIFY, false)
        val middleFontSize = prefs.getBoolean(MIDDLE_FONT_SIZE, false)
        val followSystemTheme = prefs.getBoolean(FOLLOW_SYSTEM_THEME, false)
        val chasingMode = prefs.getBoolean(CHASING_MODE, false)
        intent.let {
            it.putExtra(CHECK_UPDATE, checkUpdate)
            it.putExtra(SIGN_NOTIFY, signNotify)
            it.putExtra(MIDDLE_FONT_SIZE, middleFontSize)
            it.putExtra(FOLLOW_SYSTEM_THEME, followSystemTheme)
            it.putExtra(CHASING_MODE, chasingMode)
        }
        activity.startActivity(intent)
    }


    // 跳转到添加书籍界面
    fun jumpToAddLocalBookActivity(activity: AppCompatActivity) {
        val intent = Intent(activity, AddLocalBookActivity::class.java)
        activity.startActivity(intent)
    }

    fun jumpToRequestStoragePermissive(activity: AppCompatActivity) {
        val intent = Intent(activity, RequestStoragePermissive::class.java)
        activity.startActivity(intent)
    }

    const val DOCUMENT_URI_STRING = "DOCUMENT_URI_STRING"
    const val EDIT_PAGE_CODE = 1
    fun jumpToAddLocalBookEditActivity(activity: AppCompatActivity, documentUri: Uri) {
        val intent = Intent(activity, AddLocalBookEditActivity::class.java)
        intent.putExtra(DOCUMENT_URI_STRING, documentUri.toString())
        activity.startActivityForResult(intent, EDIT_PAGE_CODE)
    }

    // 将数据保存到配置文件中
    fun saveToProfile(name: String, value: Any) {
        when (value) {
            is Long -> {
                editor.putLong(name, value)
            }
            is Boolean -> {
                editor.putBoolean(name, value)
            }
            is Int -> {
                editor.putInt(name, value)
            }
        }
        editor.apply()
    }

}