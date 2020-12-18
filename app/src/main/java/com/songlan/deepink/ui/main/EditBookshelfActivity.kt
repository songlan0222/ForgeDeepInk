package com.songlan.deepink.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.songlan.deepink.R
import com.songlan.deepink.model.Book
import com.songlan.deepink.model.Bookshelf
import com.songlan.deepink.utils.LogUtil
import kotlinx.android.synthetic.main.activity_edit_bookshelf.*
import java.util.*
import kotlin.properties.Delegates

class EditBookshelfActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(EditBookshelfActivityVM::class.java)
    }

    // 判断是否为编辑状态，默认设置为不是
    private var isEditBookshelf = false

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bookshelf)

        // 配置Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.title = null

        // 获取进入Activity时对应的参数
        isEditBookshelf = intent.getBooleanExtra("edit_bookshelf", false)
        LogUtil.d("MainTest", "$isEditBookshelf")

        lateinit var bookshelf: Bookshelf
        // 如果是通过添加书架按钮进入Activity
        if (!isEditBookshelf) {
            textView_title.text = "创建书架"
            // editText 获取焦点
            editText_bookshelfName.isFocusable = true
            editText_bookshelfName.isFocusableInTouchMode = true
            editText_bookshelfName.requestFocus()

            // 确保editText绘制完成后，弹出软键盘
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    val imm = editText_bookshelfName.context
                        .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(editText_bookshelfName, 0)
                }
            }, 300)

            bookshelf = Bookshelf("")

        } else {
            textView_title.text = "编辑书架"
            val bookshelfId = intent.getLongExtra("bookshelf_id", -1)
            if (bookshelfId == -1L) {
                throw Exception("参数：bookshelfID，发生错误")
            }
            // 从数据库根据bookshelfId直接查询书架
            viewModel.loadBookshelf(bookshelfId)
            bookshelf = viewModel.checkedBookshelf
        }

        // 根据bookshelf的信息，修改界面中RadioButton的状态


        // 为保存按钮配置点击事件
        Btn_save.setOnClickListener {
            saveBookshelfInfo(bookshelf)
        }
    }

    private fun saveBookshelfInfo(bookshelf: Bookshelf) =
        // 分情况，如果是新增，则使用insert，反之update
        if (isEditBookshelf)
            viewModel.insertBookshelf(bookshelf)
        else
            viewModel.updateBookshelf(bookshelf)


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}