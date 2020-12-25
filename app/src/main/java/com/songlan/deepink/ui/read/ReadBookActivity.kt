package com.songlan.deepink.ui.read

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.songlan.deepink.AppProfiles
import com.songlan.deepink.MyApplication.Companion.context
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.activity_read_book.*


class ReadBookActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(ReadBookActivityVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_book)

        val bookId = intent.getLongExtra(AppProfiles.READING_BOOK_ID, -1)
        if (bookId == -1L) {
            throw Exception("致命错误：没有获取到小说id")
        }

        // 配置工具栏
        val view = View.inflate(this, R.layout.dialog_reading_tool_bar, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        val parentView = view.parent as View
        val behavior = BottomSheetBehavior.from(parentView)
        behavior.peekHeight = 730
        parentView.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
        // 配置工具栏内容
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        // toolbar.inflateMenu(R.menu.menu_read_tool_bar)
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

            }
            true
        }
        toolbar.title = "斗破苍穹"

        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // 小说内容设置
        chapterContent.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            dialog.show()
        }

        viewModel.loadChapterTitleWithBookId.observe(this, Observer { result ->
            val chapters = result.getOrNull()
            if (chapters != null) {
                viewModel.chapterTitles.clear()
                viewModel.chapterTitles.addAll(chapters)
            }
        })

        viewModel.bookLiveData.observe(this, Observer { result ->
            val book = result.getOrNull()
            if (book != null) {
                viewModel.book = book
                viewModel.loadChapterTitleWithBookId(book.bookId)
            } else {
                Log.d("MainTest", "阅读界面：获取书籍失败")
            }

        })

        viewModel.loadBook(bookId)


    }
}
