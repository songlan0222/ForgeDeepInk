package com.songlan.deepink.ui.main

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.songlan.deepink.R
import com.songlan.deepink.model.Book
import kotlinx.android.synthetic.main.fragment_bookshelf_details.*

class BookshelfDetailsFragment : Fragment(), XRecyclerView.LoadingListener {

    private lateinit var mainActivity: MainActivity

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

        main_center_toolbar.inflateMenu(R.menu.main_toolbar_menu)
        main_center_toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {

                R.id.main_bookshelf_search -> {
                }
                R.id.main_bookshelf_me -> {
                    mainActivity.changeFragment(MainActivityVM.BOOKSHELF_GROUP_OTHERS_ID)
                }
            }
            true
        }

        // 添加工具栏正中标题
        main_center_toolbar?.title = getString(R.string.bookshelf_name)

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
        val adapter = MyXRecyclerViewAdapter(mainActivity.vm.bookshelfList[MainActivityVM.CURRENT_BOOKSHELF_ID].bookList)
        val manager = GridLayoutManager(requireActivity().applicationContext, 3)
        // 为xRecyclerView配置数据
        main_center_xRecyclerView.adapter = adapter
        main_center_xRecyclerView.layoutManager = manager
        // 设置下拉监听
        main_center_xRecyclerView.setLoadingListener(this);
        main_center_xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        // 设置上次下拉刷新时间
        main_center_xRecyclerView.defaultRefreshHeaderView.setRefreshTimeVisible(true)

    }

    class MyXRecyclerViewAdapter(val bookList: List<Book>) :
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
            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val book = bookList[position]
            holder.bookImage.setImageResource(book.bookImage)
            holder.bookName.text = book.bookName
        }

        override fun getItemCount() = bookList.size
    }
}