package com.songlan.deepink.ui.local

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.v
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.songlan.deepink.AppProfiles.DOCUMENT_URI_STRING
import com.songlan.deepink.MyApplication.Companion.context
import com.songlan.deepink.R
import com.songlan.deepink.utils.ChapterDivideUtil
import kotlinx.android.synthetic.main.activity_add_local_book.*
import kotlinx.android.synthetic.main.activity_add_local_book.toolbar
import kotlinx.android.synthetic.main.activity_edit_local_book.*
import java.lang.Exception

class AddLocalBookEditActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(AddLocalBookEditActivityVM::class.java)
    }

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

        viewModel.chapterTitlesLiveData.observe(this, Observer { result ->
            val titles = result.getOrNull()
            if (titles != null) {
                viewModel.chapterTitles.clear()
                viewModel.chapterTitles.addAll(titles)
            }
        })

        // 开始处理文件
        viewModel.getChapterTitlesFromTxt(documentUri)
    }
}