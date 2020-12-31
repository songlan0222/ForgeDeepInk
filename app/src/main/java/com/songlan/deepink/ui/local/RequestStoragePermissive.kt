package com.songlan.deepink.ui.local

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.UriPermission
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.MyApplication.Companion.context
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.activity_request_storage_permissive.*


class RequestStoragePermissive : AppCompatActivity() {
    private lateinit var adapter: MyRecyclerViewAdapter
    private val viewModel by lazy {
        ViewModelProvider(this).get(RequestStoragePermissiveVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_storage_permissive)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_back)
            it.title = ""
        }

        defaultFolderPath.text =
            context.applicationContext.filesDir.absolutePath.toString() + "/books"

        viewModel.loadPersistedPermissionsLiveData.observe(this, Observer { result ->
            val permissions = result.getOrNull()
            if (permissions != null) {
                viewModel.persistedPermissions.clear()
                viewModel.persistedPermissions.addAll(permissions)
                adapter.notifyDataSetChanged()
            }
        })

        requestPermission.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            startActivityForResult(intent, 1)
        }

        persistedUri.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel.loadPersistedPermissions()
        adapter = MyRecyclerViewAdapter(viewModel.persistedPermissions)
        persistedUri.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1 -> {
                    // The result data contains a URI for the document or directory
                    data?.data?.also { uri ->
                        takePermission(uri)
                    }
                }
            }
        }
    }


    private fun takePermission(uri: Uri) {
        val contentResolver = applicationContext.contentResolver
        val takeFlags: Int =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        contentResolver.takePersistableUriPermission(uri, takeFlags)
        viewModel.loadPersistedPermissions()
    }

    fun releasePermission(uri: Uri) {
        val contentResolver = context.contentResolver
        val takeFlags: Int =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        contentResolver.releasePersistableUriPermission(uri, takeFlags)
        viewModel.loadPersistedPermissions()
    }

    inner class MyRecyclerViewAdapter(private val dataList: List<UriPermission>) :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val permissionItem: LinearLayout = view.findViewById(R.id.permissionItem)
            val permissionUri: TextView = view.findViewById(R.id.permissionUri)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_request_permission, parent, false)
            val holder = ViewHolder(view)
            holder.permissionItem.setOnClickListener {
                val position = holder.adapterPosition
                val uri = dataList[position].uri
                showBottomDialog(parent.context, uri)
            }
            return holder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val uri = dataList[position].uri
            holder.permissionUri.text = "/${uri.path?.replace("/tree/primary:", "")}"
        }

        override fun getItemCount() = dataList.size

        private fun showBottomDialog(context: Context, uri: Uri) {
            val view = View.inflate(context, R.layout.dialog_permissive_path, null)
            val title = view.findViewById<TextView>(R.id.title)
            title.text = "/${uri.path?.replace("/tree/primary:", "")}"
            val dialog = Dialog(context, R.style.DialogTheme)
            dialog.setContentView(view)

            val window = dialog.window?.let {
                it.setGravity(Gravity.BOTTOM)
                it.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                it.setWindowAnimations(R.style.main_menu_animStyle)
            }

            // 添加取消按钮点击事件
            val cancelBtn = view.findViewById<Button>(R.id.btn_cancel)
            cancelBtn.setOnClickListener {
                dialog.cancel()
            }

            // 添加删除功能
            val deleteItem = view.findViewById<LinearLayout>(R.id.deleteItem)?.setOnClickListener {
                releasePermission(uri)
                dialog.cancel()
            }

            dialog.show()
        }


    }


}