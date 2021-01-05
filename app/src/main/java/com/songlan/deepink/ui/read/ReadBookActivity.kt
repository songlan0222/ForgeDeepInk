package com.songlan.deepink.ui.read

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.songlan.deepink.AppProfiles
import com.songlan.deepink.R
import com.songlan.deepink.model.Chapter
import com.songlan.deepink.utils.LogUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_read_book.*


class ReadBookActivity : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProvider(this).get(ReadBookActivityVM::class.java)
    }

    private val fragmentMap = hashMapOf<Int, Fragment>()
    lateinit var bottomFragment: ReadBottomSheetDialog
    lateinit var chapterTitleAdapter: MyRecyclerViewAdapter

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
                Log.d("MainTest", "获取到图书: ${book.bookName}")
                viewModel.loadChapterTitleWithBookId(book.bookId)
            } else {
                Log.d("MainTest", "阅读界面：获取书籍失败")
            }
        })

        viewModel.loadChaptersWithBookId.observe(this, Observer { result ->
            val chapters = result.getOrNull()
            if (chapters != null) {
                LogUtils.v(msg="获取章节信息成功，章节数目为：${chapters.size}")
                viewModel.chapterTitles.clear()
                viewModel.chapterTitles.addAll(chapters)
                chapterTitleAdapter.notifyDataSetChanged()
            } else{
                LogUtils.v(msg="获取章节信息失败")
            }
        })

        chapterTitleAdapter = MyRecyclerViewAdapter(viewModel.chapterTitles)


        viewModel.loadBook(bookId)

        // 测试ViewPager显示
        chapterContent.adapter = ReadingPageViewAdapter(supportFragmentManager)
        chapterContent.currentItem = 1

        setBottomSheetDialog()
    }

    private fun setBottomSheetDialog() {
        bottomFragment = ReadBottomSheetDialog.getDialog()
    }

    fun showBottomSheetDialog() {
        showBottomSheetDialogFragment()
    }

    fun hideBottomSheetDialog() {
        hideBottomSheetDialogFragment()
    }

    private fun hideBottomSheetDialogFragment() {
        if (bottomFragment == null) {
            bottomFragment.dismiss()
        }
    }

    private fun showBottomSheetDialogFragment() {
        bottomFragment.show(supportFragmentManager, "bottomSheetDialogFragment")
    }


    // 书籍翻页功能的Adapter
    inner class ReadingPageViewAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount() = 3

        override fun getItem(position: Int): Fragment =
            when (position) {
                0 -> fragmentMap[0] ?: PrePageFragment()
                1 -> fragmentMap[1] ?: CurPageFragment()
                else -> fragmentMap[2] ?: LastPageFragment()
            }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val fragment = super.instantiateItem(container, position) as Fragment
            fragmentMap[position] = fragment
            return fragment
        }
    }

    // 章节列表的Adapter
    inner class MyRecyclerViewAdapter(private val chapterList: List<Chapter>) :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val chapterTitle: TextView = view.findViewById<TextView>(R.id.chapterTitle)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chapter_title, parent, false)
            val viewHolder = ViewHolder(view)
            viewHolder.itemView.setOnClickListener {
                val position = viewHolder.adapterPosition
                val chapter = chapterList[position]
            }
            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val chapter = chapterList[position]
            holder.chapterTitle.text = chapter.chapterName
        }

        override fun getItemCount() = chapterList.size
    }

}
