package com.songlan.deepink.utils

import androidx.lifecycle.liveData
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
    liveData<Result<T>>(context) {
        var result = try {
            block()
        } catch (e: Exception) {
            Result.failure<T>(e)
        }
        emit(result)
    }