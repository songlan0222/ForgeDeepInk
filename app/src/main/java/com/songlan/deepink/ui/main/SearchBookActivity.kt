package com.songlan.deepink.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.songlan.deepink.R
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
            if (content.isNotEmpty()) {
                replaceFragment(vm.searchBookResultFragment)
            } else{
                replaceFragment(vm.searchBookHistoryFragment)
            }
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

            }
        }
        return super.onOptionsItemSelected(item)
    }
}