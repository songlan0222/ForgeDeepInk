package com.songlan.deepink.ui.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.activity_edit_bookshelf.*

class EditBookshelfActivity : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bookshelf)
    }
}