package com.songlan.deepink.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.songlan.deepink.R
import com.songlan.deepink.model.Bookshelf
import androidx.lifecycle.Observer
import com.songlan.deepink.utils.LogUtil
import kotlinx.android.synthetic.main.activity_edit_bookshelf.*
import java.util.*

class EditBookshelfActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(EditBookshelfActivityVM::class.java)
    }

    // 判断是否为编辑状态，默认设置为不是
    private var isEditBookshelf = false
//    private lateinit var bookshelf: Bookshelf

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

        // 绑定observer，用于更新viewModel的数据
        viewModel.checkedBookshelfLiveData.observe(this, Observer { result ->
            val bookshelf = result.getOrNull()
            if (bookshelf != null) {
                viewModel.checkedBookshelf = bookshelf
            }
            editText_bookshelfName.setText(viewModel.checkedBookshelf.bookshelfName)
            // 根据bookshelf的信息，修改界面中RadioButton的状态
            if (viewModel.checkedBookshelf.isFirstChoose)
                firstChoose_true.isChecked = true
            else firstChoose_false.isChecked = true

            if (viewModel.checkedBookshelf.layoutWay == 0)
                layout_grid.isChecked = true
            else
                layout_list.isChecked = true

            if (viewModel.checkedBookshelf.sortWay == 0)
                sort_time.isChecked = true
            else
                sort_self_define.isChecked = true

            if (viewModel.checkedBookshelf.infoWay == 0)
                info_simple.isChecked = true
            else
                info_details.isChecked = true

        })

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
        } else {
            textView_title.text = "编辑书架"
            val bookshelfId = intent.getLongExtra("bookshelf_id", -1)
            if (bookshelfId == -1L) {
                throw Exception("参数：bookshelfID，发生错误")
            }
            viewModel.loadBookshelf(bookshelfId)
        }

        // 为保存按钮配置点击事件
        Btn_save.setOnClickListener {
            saveBookshelfInfo(viewModel.checkedBookshelf)
        }

        editText_bookshelfName.addTextChangedListener {
            viewModel.checkedBookshelf.bookshelfName = it.toString()
        }

        firstChooseGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.firstChoose_false -> viewModel.checkedBookshelf.isFirstChoose = false
                R.id.firstChoose_true -> viewModel.checkedBookshelf.isFirstChoose = true
            }
        }

        layoutGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.layout_grid -> viewModel.checkedBookshelf.layoutWay = 0
                R.id.layout_list -> viewModel.checkedBookshelf.layoutWay = 1
            }
        }

        sortGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.sort_time -> viewModel.checkedBookshelf.sortWay = 0
                R.id.sort_self_define -> viewModel.checkedBookshelf.sortWay = 1
            }
        }

        infoGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.info_simple -> viewModel.checkedBookshelf.infoWay = 0
                R.id.info_details -> viewModel.checkedBookshelf.infoWay = 1
            }
        }
    }

    private fun saveBookshelfInfo(bookshelf: Bookshelf) {
        // 分情况，如果是新增，则使用insert，反之update
        if (!isEditBookshelf) {
            viewModel.insertBookshelfLiveData.observe(this, androidx.lifecycle.Observer { result ->
                val bookshelfId = result.getOrNull()
                if (bookshelfId != null) {
                    LogUtil.v("MainTest", "insert_bookshelf_id = $bookshelfId")
                }
            })
            viewModel.insertBookshelf(bookshelf)
        } else {
            viewModel.updateBookshelfLiveData.observe(this, androidx.lifecycle.Observer { result ->
                val bookshelfId = result.getOrNull()
                if (bookshelfId != null) {
                    LogUtil.v("MainTest", "insert_bookshelf_id = $bookshelfId")
                }
            })
            viewModel.updateBookshelf(bookshelf)
        }

        val intent = Intent()
        intent.putExtra("refresh_bookshelfs", true)
        setResult(RESULT_OK, intent)
        finish()
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