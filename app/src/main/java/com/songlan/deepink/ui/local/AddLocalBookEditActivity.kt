package com.songlan.deepink.ui.local

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.v
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.AppProfiles.DOCUMENT_URI_STRING
import com.songlan.deepink.MyApplication.Companion.context
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.activity_add_local_book.toolbar
import kotlinx.android.synthetic.main.activity_edit_local_book.*
import java.lang.Exception

class AddLocalBookEditActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(AddLocalBookEditActivityVM::class.java)
    }
    private lateinit var adapter: MyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_local_book)

        // 根据传递的字符串，转换成Uri，并根据Uri获取文件
        var documentUriString =
            intent.getStringExtra(DOCUMENT_URI_STRING) ?: throw Exception("获取本地文件失败，请重试")
        val documentUri = Uri.parse(documentUriString)
        val documentFile = DocumentFile.fromSingleUri(context, documentUri)!!
        v("MainTest", "获取到文件：${documentFile.name}")

        // 界面加载
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.title = ""
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_back)
        }
        val documentName = documentFile.name!!
        val lastPointIndex = documentName.lastIndexOf(".")
        bookNameTitle.text = documentName.substring(0, lastPointIndex)
        bookNameEditText.setText(documentName.substring(0, lastPointIndex))
        // 作者佚名，暂时不填
        bookAuthorEditText.setText("")


        viewModel.chapterTitlesLiveData.observe(this, Observer { result ->
            val titles = result.getOrNull()
            if (titles != null) {
                viewModel.chapterTitles.clear()
                viewModel.chapterTitles.addAll(titles)
                adapter.notifyDataSetChanged()
            }
        })

        // 开始处理文件
        viewModel.getChapterTitlesFromTxt(documentUri)

        adapter = MyRecyclerViewAdapter(viewModel.chapterTitles)
        chapterNameRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        chapterNameRecyclerView.adapter = adapter
    }

    inner class MyRecyclerViewAdapter(private val dataList: List<String>) :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val chapterItem: LinearLayout = view.findViewById(R.id.chapterItem)
            val checkItem: CheckBox = view.findViewById(R.id.checkItem)
            val chapterName: TextView = view.findViewById(R.id.chapterName)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_add_local_book_chapter, parent, false)
            val viewHolder = ViewHolder(view)
            viewHolder.chapterItem.setOnClickListener {
                val position = viewHolder.adapterPosition
                val chapter = dataList[position]
                viewHolder.checkItem.isChecked = !viewHolder.checkItem.isChecked
            }
            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val chapter = dataList[position]
            holder.chapterName.text = chapter
            holder.checkItem.isChecked = true
        }

        override fun getItemCount() = dataList.size
    }
}