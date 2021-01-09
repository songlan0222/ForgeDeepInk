package com.songlan.deepink.ui.read

import android.os.Bundle
import android.view.LayoutInflater
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
    private lateinit var pageList: MutableList<String>
    private lateinit var preChapterPageList: MutableList<String>
    private lateinit var nextChapterPageList: MutableList<String>
    private var curPageNum = 0
    private var hasPreChapter = false
    private var hasNextChapter = false

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
                viewModel.getChapterContent()
                viewModel.getPreChapterContent()
                viewModel.getNextChapterContent()
                chapterTitleAdapter.notifyDataSetChanged()
            } else {
                LogUtils.v(msg = "获取正在阅读的章节信息失败")
            }
        })
        // 获取上一章
        viewModel.preChapterLiveData.observe(this, Observer { result ->
            val content = result.getOrNull()
            if (content != null) {
                hasPreChapter = true
                viewModel.preChapter = content
                preChapterPageList = getPageList(content)
            } else {
                hasPreChapter = false
            }
        })
        // 获取下一章
        viewModel.nextChapterLiveData.observe(this, Observer { result ->
            val content = result.getOrNull()
            if (content != null) {
                hasNextChapter = true
                viewModel.nextChapter = content
                nextChapterPageList = getPageList(content)
            } else {
                hasNextChapter = false
            }
        })
        // 获取当前章
        viewModel.curChapterLiveData.observe(this, Observer { result ->
            val content = result.getOrNull()
            if (content != null) {
                LogUtils.v(msg = "获取正在阅读章节内容成功")
                viewModel.readingChapterContent = content
                curReadPage.text = content
                pageList = getPageList(content)
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
//                        if (curPageNum <= 0) {
//                            curPageNum = 0
//                        }
                        chapterContent.setCurrentItem(1, false)
                    }
                    2 -> {
                        curPageNum++
                        LogUtils.v(msg = "翻页中：curPageNum=$curPageNum")
//                        if (curPageNum >= pageList.size) {
//                            curPageNum = pageList.size - 1
//                        }
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
    private fun getPageList(content: StringBuilder): MutableList<String> {
        curReadPage.resize()
        val charNum = curReadPage.getCharNum()
        var i = 0
        val contentStrLen = content.toString().length
        var tempContent = content.toString()
        val pageList = mutableListOf<String>()
        while (i < contentStrLen) {
            var pageContent: String
            // 如果当前字数比页面可容纳字数少时
            if (charNum > tempContent.length) {
                pageContent = tempContent
            } else {
                pageContent = tempContent.substring(0, charNum)
                tempContent = tempContent.substring(charNum)
            }
            pageList.add(pageContent)
            i += charNum
            if (i > contentStrLen) {
                i = contentStrLen
            }
        }
        return pageList
    }

    // 为页面填充小说内容
    private fun setPageContent(isToPre: Boolean = false) {
        LogUtils.v(msg = "curPageNum: $curPageNum")
        // 如果翻页后是第0页
        if (curPageNum == 0) {
            curReadPage.text = pageList[curPageNum]
            // 判断是否存在上一章，如果存在，加载上一章最后一页
            if (preChapterPageList.isEmpty()) {
                preReadPage.text = ""
            } else {
                preReadPage.text = preChapterPageList[preChapterPageList.size - 1]
            }

            if (pageList.size > curPageNum)
                if (nextChapterPageList.isEmpty()) {
                    nextReadPage.text = pageList[curPageNum + 1]
                } else {
                    nextReadPage.text = nextChapterPageList[0]
                }
            else {
                // 无后续内容
            }
        }
        // 如果翻页后是最后一页
        else if (curPageNum == pageList.size - 1) {
            if (!isToPre) {
                preReadPage.text = pageList[curPageNum - 1]
                curReadPage.text = pageList[curPageNum]
                // 后一页填充
                if (nextChapterPageList.isEmpty()) {
                    nextReadPage.text = ""
                } else {
                    nextReadPage.text = nextChapterPageList[0]
                }
                curReadPage.text = pageList[curPageNum]
            } else {
                preReadPage.text = pageList[curPageNum - 1]
                curReadPage.text = pageList[curPageNum]
                nextReadPage.text = nextChapterPageList[0]
            }

        }
        // 如果翻页后进入下一章
        else if (curPageNum >= pageList.size) {
            // 获取下一章
            preChapterPageList.clear()
            preChapterPageList.addAll(pageList)

            pageList.clear()
            pageList.addAll(nextChapterPageList)
            curPageNum = 0

            setPageContent()
            viewModel.getNextChapterContent()
        }
        // 如果翻页后进入上一章
        else if (curPageNum < 0) {

            nextChapterPageList.clear()
            nextChapterPageList.addAll(pageList)

            // 替换当前页
            pageList.clear()
            pageList.addAll(preChapterPageList)
            curPageNum = preChapterPageList.size - 1

            // 标记为返回上一章
            setPageContent(true)
            // 获取上一章
            viewModel.getPreChapterContent()
        }
        // 正常情况下
        else {
            preReadPage.text = pageList[curPageNum - 1]
            nextReadPage.text = pageList[curPageNum + 1]
            curReadPage.text = pageList[curPageNum]
        }

    }

    /* 底部弹窗设置 */
    private fun setBottomSheetDialog() {
        bottomFragment = ReadBottomSheetDialog.getDialog()
    }

    fun showBottomSheetDialog() {
        showBottomSheetDialogFragment()
    }

    fun hideBottomSheetDialog() {
        hideBottomSheetDialogFragment()
    }

    private fun showBottomSheetDialogFragment() {
        bottomFragment.show(supportFragmentManager, "bottomSheetDialogFragment")
    }

    private fun hideBottomSheetDialogFragment() {
        bottomFragment?.dismiss()
    }

    /* 通过导航栏，切换章节 */
    fun changeReadingChapter(chapter: Chapter) {
        changeChapter(chapter)
    }

    private fun changeChapter(chapter: Chapter) {
        viewModel.loadReadingChapter(chapter.chapterId)
        viewModel.book.readingChapterId = chapter.chapterId
        viewModel.updateBook(viewModel.book)
    }

    /* 翻页时，切换章节*/
    fun toPreChapter() {
        pageList.addAll(0, preChapterPageList)
    }

    fun toNextChapter() {
        pageList.addAll(nextChapterPageList)
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
                // 选中内容切换
                chapterListSelected(viewHolder)
                // 隐藏工具栏
                hideBottomSheetDialogFragment()
                // 切换章节
                changeReadingChapter(chapter)
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
