package com.songlan.deepink.ui.local

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Formatter.formatFileSize
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.AppProfiles
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.activity_add_local_book.*
import java.text.SimpleDateFormat
import java.util.*

class AddLocalBookActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(AddLocalBookActivityVM::class.java)
    }
    private lateinit var adapter: MyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_local_book)

        setSupportActionBar(toolbar)
        supportActionBar?.let { it ->
            it.title = ""
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_back)
        }

        viewModel.loadPersistedFilesLiveData.observe(this, Observer { result ->
            val files = result.getOrNull()
            if (files != null) {
                files.forEach {
                    Log.d("MainTest", "文件名称：${it?.name}")
                }
                viewModel.persistedFiles.clear()
                viewModel.persistedFiles.addAll(files)
                adapter.notifyDataSetChanged()
            }

        })
        viewModel.loadPersistedFiles()

        adapter = MyRecyclerViewAdapter(viewModel.persistedFiles)
        localBookList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        localBookList.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_local_book, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.requestStoragePermission -> {
                AppProfiles.jumpToRequestStoragePermissive(this)
            }
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        // 刷新数据列表
    }

    inner class MyRecyclerViewAdapter(private val dataList: List<DocumentFile?>) :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val documentItem: LinearLayout = view.findViewById(R.id.documentItem)
            val docIcon: ImageView = view.findViewById(R.id.documentIcon)
            val docName: TextView = view.findViewById(R.id.documentName)
            val docInfo: TextView = view.findViewById(R.id.documentInfo)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_add_local_book, parent, false)
            val viewHolder = ViewHolder(view)
            viewHolder.documentItem.setOnClickListener {

            }
            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val document = dataList[position]
            if (document != null) {
                when (document.type) {
                    "text/plain" -> {
                        holder.docIcon.setImageResource(R.drawable.ic_document_txt)
                        holder.docName.text = document.name?.replace(".txt", "")
                    }
                    "pdf/plain" -> {
                        holder.docIcon.setImageResource(R.drawable.ic_document_pdf)
                    }
                    "epub/plain" -> {
                        holder.docIcon.setImageResource(R.drawable.ic_document_epub)
                    }
                    "mobi/plain" -> {
                        holder.docIcon.setImageResource(R.drawable.ic_document_mobi)
                    }
                    else -> {
                        holder.docIcon.setImageResource(R.drawable.ic_document_other)
                    }
                }

                // 格式需要修改
                holder.docInfo.text =
                        //"${document.length()} | ${getFormatFileLastModified(document.lastModified())}"
                    "${document.length()} | ${document.lastModified()}"
            }

        }

        override fun getItemCount() = dataList.size
    }

    fun getFormatFileSize(length: Long) {
        //return Formatter.BigDecimalLayoutForm()
    }

    fun getFormatFileLastModified(time: Long): String {
        //val simpleDateFormat = SimpleDateFormat("yyyy/NM/dd HH:mm")
        //return simpleDateFormat.format(time)

    }
}