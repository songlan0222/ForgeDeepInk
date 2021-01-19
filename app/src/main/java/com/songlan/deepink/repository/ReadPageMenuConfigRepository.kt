package com.songlan.deepink.repository

import android.content.Context
import androidx.lifecycle.liveData
import com.songlan.deepink.utils.PropertiesUtil
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.util.*
import kotlin.coroutines.CoroutineContext

object ReadPageMenuConfigRepository {

    fun loadConfig(context: Context, file: String) = fire(Dispatchers.IO) {
        val properties = PropertiesUtil.loadConfig(context, file)
        Result.success(properties)
    }

    fun saveConfig(context: Context, file: String, properties: Properties) = fire(Dispatchers.IO) {
        PropertiesUtil.saveConfig(context, file, properties)
        Result.success(Any())
    }

    // 对获取liveData进行简化
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            var result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}