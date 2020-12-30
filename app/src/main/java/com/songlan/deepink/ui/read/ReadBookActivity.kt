package com.songlan.deepink.ui.read

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.songlan.deepink.AppProfiles
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_read_book.*


class ReadBookActivity : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProvider(this).get(ReadBookActivityVM::class.java)
    }

    private val fragmentMap = hashMapOf<Int, Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_book)

        val bookId = intent.getLongExtra(AppProfiles.READING_BOOK_ID, -1)
        if (bookId == -1L) {
            throw Exception("致命错误：没有获取到小说id")
        }

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
