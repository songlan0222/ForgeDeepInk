package com.songlan.deepink.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.R
import com.songlan.deepink.model.BookSrc
import kotlinx.android.synthetic.main.activity_book_src_manage.*

class BookSrcManageActivity : AppCompatActivity() {

    private val vm by lazy {
        ViewModelProvider(this).get(BookSrcManageActivityVM::class.java)
    }

    enum class DataType {
        STAR, SRC
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_src_manage)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.title = null

        toolbar.overflowIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_main_left_add_bookshelf)

        recyclerView.adapter = MyRecyclerViewAdapter(vm.bookSrcList, DataType.SRC)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_book_src_manage, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }
        return super.onOptionsItemSelected(item)
    }

    inner class MyRecyclerViewAdapter(
        private val bookSrcList: List<BookSrc>,
        private val dataType: DataType
    ) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        inner class SrcViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }

        inner class StarViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (dataType == DataType.SRC) {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_book_src, parent, false)
                val viewHolder = SrcViewHolder(view)
                viewHolder
            } else {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_book_src, parent, false)
                val viewHolder = SrcViewHolder(view)
                viewHolder
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (dataType == DataType.SRC) {

            } else {

            }
        }

        override fun getItemCount(): Int = if (dataType == DataType.SRC) {
            bookSrcList.size
        } else 5
    }
}