package com.songlan.deepink

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class AppProfiles(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_profile", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    // 默认选中的书架ID
    private val CHECKED_BOOKSHELF_ID = "checkedBookshelfId"
    private val DEFAULT_BOOKSHELF_ID = 1L
    fun getCheckedBookshelfIdFromProfile(): Long {
        return prefs.getLong(CHECKED_BOOKSHELF_ID, DEFAULT_BOOKSHELF_ID)
    }

    fun saveCheckedBookshelfIdToProfile(bookshelfId: Long) {
        editor.putLong(CHECKED_BOOKSHELF_ID, bookshelfId)
        editor.apply()
    }

    // 配置界面参数
    fun getOtherUIParams(){
    }

}