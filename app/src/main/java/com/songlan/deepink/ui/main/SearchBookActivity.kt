package com.songlan.deepink.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.activity_search_book.*

class SearchBookActivity : AppCompatActivity() {

    private val fragmentMap = mutableMapOf<Int, Fragment>()

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
//        绑定获取焦点
//        editText_searchBookName.viewTreeObserver.addOnGlobalLayoutListener {
//
//        }
        // 弹出软键盘
        val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(editText_searchBookName, 0)

        // 配置ViewPager
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.currentItem = SearchBookActivityVM.DEFAULT_FRAGMENT

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

    private inner class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount() = 2

        override fun getItem(position: Int) =
            when (position) {
                0 -> fragmentMap[0] ?: SearchBookHistoryFragment()
                else -> fragmentMap[1] ?: SearchBookResultFragment()
            }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val fragment = super.instantiateItem(container, position) as Fragment
            fragmentMap[position] = fragment
            return fragment
        }


    }
}