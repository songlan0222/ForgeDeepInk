package com.songlan.deepink.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.songlan.deepink.AppProfiles
import com.songlan.deepink.R
import java.lang.Exception

class ReadBookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_book)

        val bookId = intent.getLongExtra(AppProfiles.READING_BOOK_ID, -1)
        if (bookId == -1L) {
            throw Exception("致命错误：没有获取到小说id")
        }

    }
}