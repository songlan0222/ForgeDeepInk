package com.songlan.deepink

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class MyApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var appProfiles: AppProfiles
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        appProfiles = AppProfiles(context)
    }


}