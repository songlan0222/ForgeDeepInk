package com.songlan.deepink.ui.local

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.v
import android.view.LayoutInflater
import android.view.MenuItem
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
import com.songlan.deepink.model.Book
import com.songlan.deepink.utils.ChapterUtil.getChaptersFromTxt
import com.songlan.deepink.utils.LogUtils
import kotlinx.android.synthetic.main.activity_add_local_book.toolbar
import kotlinx.android.synthetic.main.activity_edit_local_book.*
import java.lang.Exception

class AddLocalBookEditActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel by lazy {
        ViewModelProvider(this).get(AddLocalBookEditActivityVM::class.java)
    }
    private lateinit var adapter: MyRecyclerViewAdapter
    private lateinit var documentUri: Uri
    private lateinit var book: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_local_book)

        // 根据传递的字符串，转换成Uri，并根据Uri获取文件
        var documentUriString =
            intent.getStringExtra(DOCUMENT_URI_STRING) ?: throw Exception("获取本地文件失败，请重试")
        documentUri = Uri.parse(documentUriString)
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
        // 章节
        adapter = MyRecyclerViewAdapter(viewModel.chapterTitles)
        chapterNameRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        chapterNameRecyclerView.adapter = adapter

        // 设置列表和进度条的显示
        chapterNameRecyclerView.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE

        // 创建Book对象
        book = Book(
            R.drawable.ic_book_default,
            documentName.substring(0, lastPointIndex),
            bookAuthorEditText.text.toString()
        )

        // 配置LiveData
        viewModel.chapterTitlesLiveData.observe(this, Observer { result ->
            val titles = result.getOrNull()
            if (titles != null) {
                viewModel.chapterTitles.clear()
                viewModel.chapterTitles.addAll(titles)
                // 设置列表和进度条的显示
                progressBar.visibility = View.GONE
                chapterNameRecyclerView.visibility = View.VISIBLE
                // 通知数据修改
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.chapterLiveData.observe(this, Observer { result ->
            val chapterId = result.getOrNull()
            if (chapterId != null) {
                LogUtils.v(msg = "添加章节成功，章节id为：$chapterId")
            }
        })

        viewModel.insertBookLiveData.observe(this, Observer { result ->
            val bookId = result.getOrNull()
            if (bookId != null) {
                book.bookId = bookId
                // 书籍保存成功后，开始章节切割并保存到本地
                getChaptersFromTxt(viewModel, documentUri, book)

                val intent = Intent()
                setResult(RESULT_OK, intent)
                finish()
            }
        })

        // 开始处理文件
        viewModel.getChapterTitlesFromTxt(documentUri)

        importBtn.setOnClickListener(this)
        regexApplyBtn.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.regexApplyBtn -> {
                // 修改Book的章节划分正则

            }
            R.id.importBtn -> {
                viewModel.insertBook(book)
            }
        }
    }
}