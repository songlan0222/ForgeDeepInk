package com.songlan.deepink.ui.base

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

abstract class AbstractBaseActivity: AppCompatActivity() {

    abstract fun loadPreference(): SharedPreferences
    abstract fun initPreference()
    abstract fun savePreference(map: Map<String, Any>)
}