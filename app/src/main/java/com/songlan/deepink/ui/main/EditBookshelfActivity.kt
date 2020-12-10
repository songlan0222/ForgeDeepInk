package com.songlan.deepink.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.input.InputManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.activity_edit_bookshelf.*

class EditBookshelfActivity : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bookshelf)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.title = null
        textView_title.text = getString(R.string.edit_bookshelf_info)

        // editText 获取焦点
        editText_bookshelfName.isFocusable = true
        editText_bookshelfName.isFocusableInTouchMode = true
        editText_bookshelfName.requestFocus()
        // 弹出软键盘
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(editText_bookshelfName, 0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}