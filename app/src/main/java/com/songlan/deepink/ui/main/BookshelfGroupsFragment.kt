package com.songlan.deepink.ui.main

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.AppProfiles
import com.songlan.deepink.AppProfiles.CHECKED_BOOKSHELF_ID
import com.songlan.deepink.AppProfiles.saveToProfile
import com.songlan.deepink.MyApplication
import com.songlan.deepink.R
import com.songlan.deepink.model.Book
import com.songlan.deepink.model.Bookshelf
import com.songlan.deepink.repository.DatabaseRepository
import com.songlan.deepink.ui.main.base.BaseFragment
import com.songlan.deepink.utils.LogUtil
import kotlinx.android.synthetic.main.fragment_bookshelf_groups.*

class BookshelfGroupsFragment : BaseFragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var bookshelfListAdapter: MyRecyclerViewAdapter

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 配置顶部工具栏
        main_left_toolbar.inflateMenu(R.menu.menu_bookshelf_group_toolbar)
        main_left_toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.main_left_add_bookshelf -> {
                    val intent = Intent(this.context, EditBookshelfActivity::class.java)
                    intent.putExtra("edit_bookshelf", false)
                    startActivityForResult(intent, 1)
                }
            }
            false
        }

        mainActivity.vm.bookshelfListLiveData.observe(mainActivity, Observer { result ->
            val bookshelfList = result.getOrNull()
            if (bookshelfList != null) {
                mainActivity.vm.bookshelfList.clear()
                mainActivity.vm.bookshelfList.addAll(bookshelfList)
                bookshelfListAdapter.notifyDataSetChanged()
                LogUtil.d("MainTest", "获取全部书架成功。")
            } else {
                LogUtil.d("MainTest", "获取全部书架时发生意外。")
            }
        })

        mainActivity.vm.deleteBookshelfLiveData.observe(mainActivity, Observer { result ->
            val deleteResult = result.getOrNull()
            if (deleteResult != null) {
                mainActivity.vm.loadBookshelfList()
            }
        })

//        mainActivity.vm.loadBooksWithBookshelfIdLiveData.observe(
//            mainActivity,
//            Observer { result ->
//                val books = result.getOrNull()
//                if (books != null) {
//                    mainActivity.vm.booksWithBookshelfId.clear()
//                    mainActivity.vm.booksWithBookshelfId.addAll(books)
//                }
//            })

        mainActivity.vm.loadBookshelfList()

        // 配置书架展示部分
        val manager = LinearLayoutManager(activity)
        bookshelfListAdapter = MyRecyclerViewAdapter(mainActivity.vm.bookshelfList)
        main_left_bookshelf_recycler.layoutManager = manager
        main_left_bookshelf_recycler.adapter = bookshelfListAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> if (resultCode == RESULT_OK) {
                val returnedData = data?.getBooleanExtra("refresh_bookshelfs", false)
                if (returnedData!!) {
                    mainActivity.vm.loadBookshelfList()
                }
            }
        }
    }

    inner class MyRecyclerViewAdapter(private val bookshelfList: List<Bookshelf>) :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
        private val checkBoxList = mutableListOf<CheckBox>()

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
                showBottomDialog(position)
            }
            return holder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val bookshelf = bookshelfList[position]

            val loadBooksWithBookshelfIdLiveData =
                DatabaseRepository.loadBooksWithBookshelfId(bookshelf.bookshelfId)
            loadBooksWithBookshelfIdLiveData.observe(
                mainActivity,
                Observer { result ->
                    val books = result.getOrNull()
                    if (books != null) {
                        holder.bookshelfDetails.adapter = DetailsAdapter(books)
                    } else{
                        holder.bookshelfDetails.adapter = DetailsAdapter(arrayListOf())
                    }
                })
            // 加载当前书架书籍信息
            // mainActivity.vm.loadBooksWithBookshelfId(bookshelf.bookshelfId)

            // 根据配置文件来判定当前书架是否选中
            holder.bookshelfItemChecked.isChecked =
                bookshelf.bookshelfId == AppProfiles.getCheckedBookshelfIdFromProfile()
            // 将书架对象加入数组，方便统一管理
            checkBoxList.add(holder.bookshelfItemChecked)
            // 当书架被选中时，将其他书架设置为不选中
            holder.bookshelfItemChecked.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) checkBoxList.forEach { checkBox ->
                    if (checkBox != buttonView && checkBox.isChecked) {
                        checkBox.isChecked = false
                    } else {
                        mainActivity.vm.loadCheckedBookshelf(bookshelf.bookshelfId)
                    }
                }
                // 设置完成后保存选中信息
                saveToProfile(CHECKED_BOOKSHELF_ID, bookshelf.bookshelfId)
            }
            holder.bookshelfName.text = bookshelf.bookshelfName

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

            // 添加取消按钮点击事件
            val cancelBtn = view.findViewById<Button>(R.id.btn_cancel)
            cancelBtn.setOnClickListener {
                bottomDialog.cancel()
            }

            // 添加[编辑书架]按钮的响应事件
            val manageBookshelfLayout =
                view.findViewById<LinearLayout>(R.id.linearLayout_manageBookshelf)
            manageBookshelfLayout.setOnClickListener {
                val intent = Intent(context, EditBookshelfActivity::class.java)
                intent.putExtra("edit_bookshelf", true)
                intent.putExtra("bookshelf_id", bookshelfList[position].bookshelfId)
                startActivityForResult(intent, 1)
                bottomDialog.cancel()
            }

            val deleteBookshelfLayout =
                view.findViewById<LinearLayout>(R.id.linearLayout_deleteBookshelf)
            deleteBookshelfLayout.setOnClickListener {
                mainActivity.vm.deleteBookshelf(bookshelfList[position].bookshelfId)
                bottomDialog.cancel()
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