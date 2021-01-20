package com.songlan.deepink.repository

import android.app.Activity
import android.content.Context
import androidx.lifecycle.liveData
import com.songlan.deepink.utils.ConfigUtil
import com.songlan.deepink.utils.fire
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.util.*
import kotlin.coroutines.CoroutineContext

object ConfigRepository {

    /**
     * 加载配置文件， 并返回map
     */
//    fun loadActivityPreference() = fire(Dispatchers.IO) {
//        val properties = ConfigUtil.loadActivityPreference()
//        Result.success(properties)
//    }

    /**
     * 将map保存到配置文件中
     */
//    fun saveConfig(activity: Activity, map: Map<String, Any>) = fire(Dispatchers.IO) {
//        ConfigUtil.saveActivityPreference(activity, map)
//        Result.success(Any())
//    }
}