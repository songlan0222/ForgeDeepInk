package com.songlan.deepink.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.songlan.deepink.AppProfiles
import com.songlan.deepink.R
import com.songlan.deepink.model.Chapter
import com.songlan.deepink.utils.LogUtils
import kotlinx.android.synthetic.main.activity_read_book.*
import kotlinx.android.synthetic.main.fragment_current_page.*
import kotlinx.android.synthetic.main.fragment_last_page.*
import kotlinx.android.synthetic.main.fragment_pre_page.*
import java.lang.StringBuilder


class ReadBookActivity : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProvider(this).get(ReadBookActivityVM::class.java)
    }

    private val fragmentMap = hashMapOf<Int, Fragment>()
    lateinit var bottomFragment: ReadBottomSheetDialog
    lateinit var chapterTitleAdapter: MyRecyclerViewAdapter
    lateinit var readPageAdapter: ReadingPageViewAdapter
    private lateinit var pageList: ArrayList<String>
    private var curPageNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_book)

        val bookId = intent.getLongExtra(AppProfiles.READING_BOOK_ID, -1)
        if (bookId == -1L) {
            throw Exception("致命错误：没有获取到小说id")
        }

        /* 数据加载 */
        // 获取书籍
        viewModel.bookLiveData.observe(this, Observer { result ->
            val book = result.getOrNull()
            if (book != null) {
                viewModel.book = book
                LogUtils.d("MainTest", "获取到图书: ${book.bookName}")
                viewModel.loadChaptersWithBookId(book.bookId)
            } else {
                LogUtils.d("MainTest", "阅读界面：获取书籍失败")
            }
        })
        // 获取章节列表
        viewModel.loadChaptersWithBookIdLiveData.observe(this, Observer { result ->
            val chapters = result.getOrNull()
            if (chapters != null) {
                LogUtils.v(msg = "获取章节信息成功，章节数目为：${chapters.size}")
                viewModel.loadChaptersWithBookId.clear()
                viewModel.loadChaptersWithBookId.addAll(chapters)

                // 获取到章节列表后，判断book.readingChapterId是否为最初值
                LogUtils.v(msg = "当前书籍正在阅读的章节为：${viewModel.book.readingChapterId}")
                if (viewModel.book.readingChapterId == -1L) {
                    // 获取小说的第一章
                    viewModel.getFirstChapterWithBookId(bookId)
                } else {
                    viewModel.loadReadingChapter(viewModel.book.readingChapterId)
                }
                chapterTitleAdapter.notifyDataSetChanged()
            } else {
                LogUtils.v(msg = "获取章节信息失败")
            }
        })
        // 获取小说的第一章
        viewModel.getFirstChapterWithBookIdLiveData.observe(this, Observer { result ->
            val firstChapter = result.getOrNull()
            if (firstChapter != null) {
                LogUtils.v(msg = "获取到的第一个章节为：${firstChapter.chapterName}id:${firstChapter.chapterId}")
                viewModel.book.readingChapterId = firstChapter.chapterId
                viewModel.updateBook(viewModel.book)
                chapterTitleAdapter.notifyDataSetChanged()
            }
        })
        // 获取正在阅读的章节
        viewModel.loadReadingChapterLiveData.observe(this, Observer { result ->
            val chapter = result.getOrNull()
            if (chapter != null) {
                LogUtils.v(msg = "获取正在阅读的章节信息成功，章节名为：${chapter.chapterName}")
                viewModel.readingChapter = chapter
                viewModel.getChapterContent(chapter)
                chapterTitleAdapter.notifyDataSetChanged()
            } else {
                LogUtils.v(msg = "获取正在阅读的章节信息失败")
            }
        })
        // 观察章节内容是否变化
        viewModel.getChapterContentLiveData.observe(this, Observer { result ->
            val content = result.getOrNull()
            if (content != null) {
                LogUtils.v(msg = "获取正在阅读章节内容成功")
                viewModel.readingChapterContent = content
                curReadPage.text = content
                pageList = getPageList()
            }
        })
        // 更新书籍正在阅读的章节id
        viewModel.updateBookLiveData.observe(this, Observer { result ->
            val res = result.getOrNull()
            if (res != null) {
                viewModel.loadBook(bookId)
                viewModel.loadReadingChapter(viewModel.book.readingChapterId)
            }
        })

        /* 数据配置 */
        viewModel.loadBook(bookId)
        chapterTitleAdapter = MyRecyclerViewAdapter(viewModel.loadChaptersWithBookId)

        // 测试ViewPager显示
        readPageAdapter = ReadingPageViewAdapter(supportFragmentManager)
        chapterContent.adapter = readPageAdapter
        chapterContent.currentItem = 1
        // 配置底部弹窗
        setBottomSheetDialog()

        chapterContent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    1 -> {
                        setPageContent()
                    }
                    0 -> {
                        curPageNum--
                        LogUtils.v(msg = "翻页中：curPageNum=$curPageNum")
                        if(curPageNum <= 0){
                            curPageNum = 0
                            // setPageContent()
                        }
                        chapterContent.setCurrentItem(1, false)
                    }
                    2 -> {
                        curPageNum++
                        LogUtils.v(msg = "翻页中：curPageNum=$curPageNum")
                        if(curPageNum >= pageList.size){
                            curPageNum = pageList.size-1
                            // setPageContent()
                        }
                        chapterContent.setCurrentItem(1, false)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }



    /* 加载章节内容 */
    // 按字数对章节分页
    private fun getPageList(): ArrayList<String> {
        curReadPage.resize()
        val charNum = curReadPage.getCharNum()
        var i = 0
        var content = viewModel.readingChapterContent.toString()
        val pageList = arrayListOf<String>()
        while (i < viewModel.readingChapterContent.length) {
            var pageContent: String
            if (charNum > content.length) {
                pageContent = content
                // content = content.substring(charNum)
            } else {
                pageContent = content.substring(0, charNum)
                content = content.substring(charNum)
            }
            pageList.add(pageContent)
            i += charNum
            if (i > viewModel.readingChapterContent.length) {
                i = viewModel.readingChapterContent.length
            }
        }
        return pageList
    }

    private fun setPageContent(){
        if(curPageNum == 0){
            curReadPage.text = pageList[curPageNum]
            if(pageList.size > curPageNum)
                lastReadPage.text = pageList[curPageNum + 1]
            else{
                // 无后续内容
            }
        }
        // 如果内容已到最后
        else if(curPageNum >= pageList.size - 1){
            curPageNum = pageList.size - 1
            curReadPage.text = pageList[curPageNum]
            if(curPageNum > 0){
                preReadPage.text = pageList[curPageNum - 1]
            }
        }
        // 正常情况下
        else{
            preReadPage.text = pageList[curPageNum - 1]
            curReadPage.text = pageList[curPageNum]
            lastReadPage.text = pageList[curPageNum + 1]
        }
    }

    /* 底部弹窗设置 */
    private fun setBottomSheetDialog() {
        bottomFragment = ReadBottomSheetDialog.getDialog()
    }

    fun showBottomSheetDialog() {
        showBottomSheetDialogFragment()
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

        private val holderList = arrayListOf<ViewHolder>()

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
            viewHolder.itemView.setOnClickListener {
                chapterListSelected(viewHolder)
            }
            holderList.add(viewHolder)
            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val chapter = chapterList[position]
            holder.chapterTitle.text = chapter.chapterName
            holder.chapterTitle.isSelected = false
            if (chapter == viewModel.readingChapter) {
                holder.chapterTitle.isSelected = true
            }
        }

        override fun getItemCount() = chapterList.size

        private fun chapterListSelected(holder: ViewHolder) {
            // 点击时，更换正在阅读的章节
            if (!holder.chapterTitle.isSelected) {
                holderList.forEach { holder ->
                    if (holder.chapterTitle.isSelected)
                        holder.chapterTitle.isSelected = false
                }
                holder.chapterTitle.isSelected = true
            }
        }
    }

}
