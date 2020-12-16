package com.songlan.deepink.ui.src

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
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
        COMMENTS, SRC
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
        toolbar.offsetTopAndBottom(40)

        recyclerView.adapter = MyRecyclerViewAdapter(vm.bookSrcList, DataType.SRC)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_book_src_manage, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.importSrc -> {

            }
            R.id.importWebSite -> {

            }
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class MyRecyclerViewAdapter(
        private val bookSrcList: List<BookSrc>,
        private val dataType: DataType
    ) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        inner class SrcViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val moreBtn: Button = view.findViewById<Button>(R.id.moreBtn)
        }

        inner class StarViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (dataType == DataType.SRC) {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_book_src, parent, false)
                val viewHolder = SrcViewHolder(view)
                viewHolder.moreBtn.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    showBottomDialog(position)
                }
                viewHolder
            } else {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_book_src, parent, false)
                val viewHolder = SrcViewHolder(view)
                viewHolder
            }
        }

        private fun showBottomDialog(position: Int) {
            val view = View.inflate(this@BookSrcManageActivity, R.layout.dialog_book_src, null)
            // 配置标题
            val bookSrcName = view.findViewById<TextView>(R.id.bookSrcName)
            bookSrcName.text = bookSrcList[position].name

            val dialog = Dialog(this@BookSrcManageActivity, R.style.DialogTheme)
            dialog.setContentView(view)

            // 配置Dialog
            dialog.window?.let {
                it.setGravity(Gravity.BOTTOM)
                it.setWindowAnimations(R.style.main_menu_animStyle)
                it.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }

            // 配置点击事件
            val cancelBtn = view.findViewById<Button>(R.id.cancelBtn)
            cancelBtn.setOnClickListener {
                dialog.cancel()
            }

            val commentsSrc = view.findViewById<LinearLayout>(R.id.commentsSrc)
            commentsSrc.setOnClickListener {

            }

            val deleteSrc = view.findViewById<LinearLayout>(R.id.deleteSrc)
            deleteSrc.setOnClickListener {

            }

            dialog.show()

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