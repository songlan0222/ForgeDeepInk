package com.songlan.deepink.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hjq.bar.OnTitleBarListener
import com.songlan.deepink.MyApplication
import com.songlan.deepink.R
import com.songlan.deepink.model.Option
import com.songlan.deepink.ui.main.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_bookshelf_others.*


class BookshelfOthersFragment : BaseFragment() {

    private val topOptionItemList = mutableListOf<Option>()
    private val bottomOptionItemList = mutableListOf<Option>()

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (activity != null) {
            mainActivity = activity as MainActivity
        }

        val view = inflater.inflate(R.layout.fragment_bookshelf_others, container, false)
        topOptionItemList.add(Option(R.drawable.ic_main_right_scan_local, "图书"))
        topOptionItemList.add(Option(R.drawable.ic_main_right_manage_book_src, "书源"))
        topOptionItemList.add(Option(R.drawable.ic_main_right_rank, "排行"))
        bottomOptionItemList.add(Option(R.drawable.ic_main_right_rss, "RSS"))
        bottomOptionItemList.add(Option(R.drawable.ic_main_right_community, "社区"))
        bottomOptionItemList.add(Option(R.drawable.ic_main_right_settings, "设置"))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 配置顶部TitleBar
        main_right_titleBar.leftIcon =
            ContextCompat.getDrawable(MyApplication.context, R.drawable.ic_main_right_experiencer)
        main_right_titleBar.rightIcon =
            ContextCompat.getDrawable(MyApplication.context, R.drawable.ic_main_right_subscriber)

        // 添加点击事件
        main_right_titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View?) {
            }

            // 此处用不上中间的点击事件
            override fun onTitleClick(v: View?) {
            }

            override fun onRightClick(v: View?) {
            }
        })


        // 配置列表
        val adapterTop = MyRecyclerViewAdapter(topOptionItemList)
        val managerTop = LinearLayoutManager(requireContext())
        main_right_recyclerView_top.adapter = adapterTop
        main_right_recyclerView_top.layoutManager = managerTop

        val adapterBottom = MyRecyclerViewAdapter(bottomOptionItemList)
        val managerBottom = LinearLayoutManager(requireContext())
        main_right_recyclerView_bottom.adapter = adapterBottom
        main_right_recyclerView_bottom.layoutManager = managerBottom
    }

    inner class MyRecyclerViewAdapter(private val itemList: List<Option>) :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val itemImage: ImageView = view.findViewById(R.id.itemImage)
            val itemName: TextView = view.findViewById(R.id.itemName)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.item_bookshelf_others, parent, false)
            val viewHolder = ViewHolder(view)
            viewHolder.itemView.setOnClickListener {

            }
            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = itemList[position]
            holder.itemImage.setImageResource(item.itemImageId)
            holder.itemName.text = item.itemImageName
        }

        override fun getItemCount() = itemList.size
    }

    // 重写点击返回按钮时需要调用的方法
    override fun onBackPressed(): Boolean {
        mainActivity.changeFragment(MainActivityVM.BOOKSHELF_DETAILS_FRAGMENT_ID)
        return true
    }
}