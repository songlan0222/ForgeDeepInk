package com.songlan.deepink.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.input.InputManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.activity_edit_bookshelf.*

class EditBookshelfActivity : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bookshelf)
        val isEditBookshelf = intent.getBooleanExtra("edit_bookshelf", false)
        Log.d("MainTest", "$isEditBookshelf")
        if (!isEditBookshelf) {
            textView_title.text = "创建书架"
            // editText 获取焦点
            editText_bookshelfName.isFocusable = true
            editText_bookshelfName.isFocusableInTouchMode = true
            editText_bookshelfName.requestFocus()
            // 弹出软键盘
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.showSoftInput(editText_bookshelfName, 0)
        } else {
            textView_title.text = "编辑书架"
            val bookshelfId = intent.getIntExtra("bookshelf_id", -1)
            if (bookshelfId == -1) {
                throw Exception("参数：bookshelfID，发生错误")
            }
            // 从数据库根据bookshelfId直接查询书架

        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.title = null

        // 为保存按钮配置点击事件
        Btn_save.setOnClickListener {
            saveBookshelfInfo()
        }
    }

    private fun saveBookshelfInfo() {

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