package com.songlan.deepink.ui.read

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
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

    lateinit var bottomFragment : ReadBottomSheetDialog

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

        // 测试ViewPager显示
        chapterContent.adapter = ReadingPageViewAdapter(supportFragmentManager)
        chapterContent.currentItem = 1

        setBottomSheetDialog()
    }

    private fun setBottomSheetDialog(){

        //ReadBottomSheetDialog().show(supportFragmentManager, "ReadBottomSheetDialog")
        //bottomFragment = ReadBottomSheetDialog.getReadBottomSheetDialog()
        bottomFragment = ReadBottomSheetDialog.getDialog()


//        val view = View.inflate(
//            this,
//            R.layout.dialog_reading_tool_bar,
//            null
//        )
//        view.layoutParams = ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT, getThreeQuarterWindowHeight()
//        )
//
//        dialog = BottomSheetDialog(this)
//        dialog.setContentView(view)
//        val parentView = view.parent as View
//        val behavior = BottomSheetBehavior.from(parentView)
//        behavior.peekHeight = getQuarterWindowHeight()
//        parentView.setBackgroundColor(
//            ContextCompat.getColor(
//                this,
//                R.color.transparent
//            )
//        )
//

    }
    fun showBottomSheetDialog(){
        showBottomSheetDialogFragment()
    }
    fun hideBottomSheetDialog(){
        hideBottomSheetDialogFragment()
    }

    fun hideBottomSheetDialogFragment(){
        if(bottomFragment == null){
            bottomFragment.dismiss()
        }
    }

    fun showBottomSheetDialogFragment(){
        //ReadBottomSheetDialog().show(supportFragmentManager, "ReadBottomSheetDialog")
        bottomFragment.show(supportFragmentManager, "bottomSheetDialogFragment")
    }





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

}
