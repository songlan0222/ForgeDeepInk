package com.songlan.deepink.repository

import com.songlan.deepink.AppDatabase
import com.songlan.deepink.MyApplication.Companion.context

object DatabaseRepository {

    val bookDao = AppDatabase.getDatabase(context).bookDao()
    val bookshelfDao = AppDatabase.getDatabase(context).bookshelfDao()
}