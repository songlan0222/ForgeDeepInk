package com.songlan.deepink.ui.main

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.songlan.deepink.R
import com.songlan.deepink.model.Book
import com.songlan.deepink.ui.search.SearchBookActivity
import com.songlan.deepink.utils.LogUtil
import kotlinx.android.synthetic.main.fragment_bookshelf_details.*

class BookshelfDetailsFragment : Fragment(), XRecyclerView.LoadingListener {

    private lateinit var mainActivity: MainActivity
    private lateinit var xRecyclerViewAdapter: MyXRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MainTest", "加载中央Fragment")
        if (activity != null) {
            mainActivity = activity as MainActivity
        }

        return inflater.inflate(R.layout.fragment_bookshelf_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        main_center_toolbar.inflateMenu(R.menu.menu_bookshelf_details_toolbar)
        main_center_toolbar.setOnMenuItemClickListener { item ->
            Log.d("MainTest", item.itemId.toString())
            when (item.itemId) {
                android.R.id.title -> {
                    mainActivity.changeFragment(MainActivityVM.BOOKSHELF_GROUP_FRAGMENT_ID)
                }
                // 跳转到搜书页面
                R.id.btn_search -> {
                    val intent = Intent(this.context, SearchBookActivity::class.java)
                    startActivity(intent)
                }
                // 切换Fragment到BookshelfOthers
                R.id.btn_me -> {
                    mainActivity.changeFragment(MainActivityVM.BOOKSHELF_OTHERS_FRAGMENT_ID)
                }
            }
            true
        }

        // 添加工具栏正中标题
        // main_center_toolbar.title = getString(R.string.bookshelf_name)

        title.text = getString(R.string.bookshelf_name)
        title.setOnClickListener {
            Log.d("MainTest", "点击标题")
            mainActivity.changeFragment(MainActivityVM.BOOKSHELF_GROUP_FRAGMENT_ID)
        }

        mainActivity.vm.bookListLiveData.observe(mainActivity, Observer { result ->
            val bookList = result.getOrNull()
            if (bookList != null) {
                mainActivity.vm.checkedBookList.clear()
                mainActivity.vm.checkedBookList.addAll(bookList)
                xRecyclerViewAdapter.notifyDataSetChanged()
            } else {
                LogUtil.d("MainTest", "获取书架书籍时发生意外。")
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        mainActivity.vm.bookshelfLiveData.observe(mainActivity, Observer { result ->
            val bookshelf = result.getOrNull()
            if (bookshelf != null) {
                mainActivity.vm.checkedBookshelf = bookshelf
                xRecyclerViewAdapter.notifyDataSetChanged()
            } else {
                LogUtil.d("MainTest", "获取书架时发生意外。")
                result.exceptionOrNull()?.printStackTrace()
            }
        })

        // 获取书籍
        mainActivity.vm.checkedBookshelf(1)
        mainActivity.vm.getBookshelfList()

        // 配置xRecyclerView
        onProgress()
    }

    // 下拉刷新
    override fun onRefresh() {
        Log.d("MainTest", "下拉刷新")
        // 刷新时延迟2秒效果
        Handler().postDelayed(Runnable { main_center_xRecyclerView.refreshComplete() }, 2000)

    }

    // 上拉加载
    override fun onLoadMore() {
        Log.d("MainTest", "上拉加载")
        main_center_xRecyclerView.refreshComplete()
    }

    private fun onProgress() {
        xRecyclerViewAdapter =
            MyXRecyclerViewAdapter(mainActivity.vm.checkedBookList)
        val manager = GridLayoutManager(requireActivity().applicationContext, 3)
        // 为xRecyclerView配置数据
        main_center_xRecyclerView.adapter = xRecyclerViewAdapter
        main_center_xRecyclerView.layoutManager = manager
        // 设置下拉监听
        main_center_xRecyclerView.setLoadingListener(this);
        main_center_xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        // 设置上次下拉刷新时间
        main_center_xRecyclerView.defaultRefreshHeaderView.setRefreshTimeVisible(true)

    }

    class MyXRecyclerViewAdapter(private val bookList: List<Book>) :
        RecyclerView.Adapter<MyXRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val bookImage: ImageView = view.findViewById(R.id.bookImage)
            val bookName: TextView = view.findViewById(R.id.bookName)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bookshelf_details_book, parent, false)
            val viewHolder = ViewHolder(view)
            viewHolder.itemView.setOnClickListener {

            }
            viewHolder.itemView.setOnLongClickListener {
                val position = viewHolder.adapterPosition
                showBookManageDialog(parent.context, position)
                true
            }
            return viewHolder
        }

        private fun showBookManageDialog(context: Context, position: Int) {
            val view = View.inflate(context, R.layout.dialog_book_options, null)
            val book = bookList[position - 1]
            val bookshelfName = view.findViewById<TextView>(R.id.textView_bookNameWithAuthor)
            // 需要补充作者信息
            bookshelfName.text = "《${book.bookName.toString()}》"

            val bottomDialog = Dialog(context, R.style.DialogTheme)
            bottomDialog.setContentView(view)

            val window = bottomDialog.window?.let {
                it.setGravity(Gravity.BOTTOM)
                it.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                it.setWindowAnimations(R.style.main_menu_animStyle)
            }

            // 添加取消按钮点击事件
            val cancelBtn = view.findViewById<Button>(R.id.btn_cancel)
            cancelBtn.setOnClickListener {
                bottomDialog.cancel()
            }
            bottomDialog.show()

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val book = bookList[position]
            holder.bookImage.setImageResource(book.bookImage)
            holder.bookName.text = book.bookName
        }

        override fun getItemCount() = bookList.size
    }
}