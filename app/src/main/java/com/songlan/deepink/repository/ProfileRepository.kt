package com.songlan.deepink.repository

import android.content.Context
import androidx.lifecycle.liveData
import com.songlan.deepink.MyApplication.Companion.context
import com.songlan.deepink.utils.ReadPageProfileUtil
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

object ProfileRepository {

    fun loadReadPageProfile() = fire(Dispatchers.IO) {
        val loadReadPageProfile = ReadPageProfileUtil.loadReadPageProfile()
        Result.success(loadReadPageProfile)
    }

    fun saveReadPageProfile(resultMap: Map<String, Float>) = fire(Dispatchers.IO) {
        ReadPageProfileUtil.saveReadPageProfile(resultMap)
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