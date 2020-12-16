package com.songlan.deepink.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.songlan.deepink.R
import com.songlan.deepink.ui.src.BookSrcManageActivity
import kotlinx.android.synthetic.main.activity_search_book.*

class SearchBookActivity : AppCompatActivity() {

    private val fragmentMap = mutableMapOf<Int, Fragment>()
    val vm by lazy {
        ViewModelProvider(this).get(SearchBookActivityVM::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_book)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.title = null

        // 获取焦点
        editText_searchBookName.isFocusable = true
        editText_searchBookName.isFocusableInTouchMode = true
        editText_searchBookName.requestFocus()
        // 弹出软键盘
        val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(editText_searchBookName, 0)

        editText_searchBookName.addTextChangedListener {
            val content = it.toString()
            vm.searchBookName = content
            if (content.isNotEmpty()) {
                replaceFragment(vm.searchBookResultFragment)
                imageView_cancelInput.visibility = View.VISIBLE
            } else {
                replaceFragment(vm.searchBookHistoryFragment)
                imageView_cancelInput.visibility = View.INVISIBLE
            }
        }

        frameLayout_cancelInput.setOnClickListener {
            editText_searchBookName.text.clear()
        }

        // 动态配置FrameLayout
        replaceFragment(vm.searchBookHistoryFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout_search, fragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_book_search_src, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.bookSrc -> {
                val intent = Intent(this, BookSrcManageActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}