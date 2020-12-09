package com.songlan.deepink.ui.main

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.R
import com.songlan.deepink.model.Book
import com.songlan.deepink.model.Bookshelf
import com.songlan.deepink.ui.main.`interface`.BackHandleInterface
import com.songlan.deepink.ui.main.base.BaseFragment
import kotlinx.android.synthetic.main.dialog_bookshelf_options.*
import kotlinx.android.synthetic.main.fragment_bookshelf_details.*
import kotlinx.android.synthetic.main.fragment_bookshelf_groups.*

class BookshelfGroupsFragment : BaseFragment() {

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity != null) {
            mainActivity = activity as MainActivity
        }
        return inflater.inflate(R.layout.fragment_bookshelf_groups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 配置顶部工具栏
        main_left_toolbar.inflateMenu(R.menu.main_left_toolbar_menu)
        main_left_toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.main_left_add_bookshelf -> {
                    val intent = Intent(this.context, EditBookshelfActivity::class.java)
                    startActivity(intent)
                }
            }
            false
        }

        // 配置书架展示部分
        val manager = LinearLayoutManager(activity)
        val adapter = MyRecyclerViewAdapter(mainActivity.vm.bookshelfList)
        main_left_bookshelf_recycler.layoutManager = manager
        main_left_bookshelf_recycler.adapter = adapter
    }

    inner class MyRecyclerViewAdapter(private val bookshelfList: List<Bookshelf>) :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val bookshelfItemChecked: CheckBox = view.findViewById(R.id.itemCheckbox)
            val bookshelfName: TextView = view.findViewById(R.id.itemName)
            val bookshelfDetails: RecyclerView = view.findViewById(R.id.item_main_left_details)
            val bookshelfMore: ImageButton = view.findViewById(R.id.itemMore)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.item_bookshelf_groups, parent, false)
            val holder = ViewHolder(view)
            holder.bookshelfMore.setOnClickListener {
                val position = holder.adapterPosition
                Log.d("MainTest", "current position: $position")
                showBottomDialog(position)
            }
            return holder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val bookshelf = bookshelfList[position]
            holder.bookshelfItemChecked.isChecked = true
            holder.bookshelfName.text = bookshelf.bookshelfName
            holder.bookshelfDetails.adapter =
                DetailsAdapter(bookshelf.bookList)
            val layoutManager = LinearLayoutManager(requireContext())
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            holder.bookshelfDetails.layoutManager = layoutManager
        }

        override fun getItemCount() = bookshelfList.size

        // 底部更多选项弹窗
        private fun showBottomDialog(position: Int) {
            val view = View.inflate(context, R.layout.dialog_bookshelf_options, null)
            // 添加书架名称
            val bookshelfName = view.findViewById<TextView>(R.id.textView_bookshelfName)
            bookshelfName.text = bookshelfList[position].bookshelfName
            val bottomDialog = Dialog(context!!, R.style.DialogTheme)
            bottomDialog.setContentView(view)

            val window = bottomDialog.window?.let { it ->
                it.setGravity(Gravity.BOTTOM)
                it.setWindowAnimations(R.style.main_menu_animStyle)
                it.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }

            bottomDialog.show()
        }

        inner class DetailsAdapter(private val bookList: List<Book>) :
            RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {
            inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
                val detailsImage: ImageView = view.findViewById(R.id.detailsImage)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view =
                    layoutInflater.inflate(R.layout.item_bookshelf_groups_book, parent, false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val book = bookList[position]
                holder.detailsImage.setImageResource(book.bookImage)
            }

            override fun getItemCount() = bookList.size
        }
    }

    // 重写点击返回按钮时调用的方法
    override fun onBackPressed(): Boolean {
        mainActivity.changeFragment(MainActivityVM.BOOKSHELF_DETAILS_FRAGMENT_ID)
        return true
    }
}