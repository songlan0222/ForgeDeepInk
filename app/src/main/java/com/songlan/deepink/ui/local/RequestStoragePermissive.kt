package com.songlan.deepink.ui.local

import android.app.Activity
import android.content.Intent
import android.content.UriPermission
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.MyApplication.Companion.context
import com.songlan.deepink.R
import com.songlan.deepink.utils.LogUtil
import kotlinx.android.synthetic.main.activity_request_storage_permissive.*


class RequestStoragePermissive : AppCompatActivity() {
    private lateinit var adapter: MyRecyclerViewAdapter

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

        requestPermission.setOnClickListener {

            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            startActivityForResult(intent, 1)
        }

        persistedUri.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val permissionList = loadPermission()
        adapter = MyRecyclerViewAdapter(permissionList)
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

    private fun loadPermission(): MutableList<UriPermission> {
        val contentResolver = applicationContext.contentResolver
        contentResolver.persistedUriPermissions.forEach {
            LogUtil.v("MainTest", "/${it.uri.path?.replace("/tree/primary:", "")}")
        }
        return contentResolver.persistedUriPermissions
    }

    private fun takePermission(uri: Uri) {
        val contentResolver = applicationContext.contentResolver
        val takeFlags: Int =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        contentResolver.takePersistableUriPermission(uri, takeFlags)
        adapter.notifyDataSetChanged()
    }

    fun releasePermission(uri: Uri) {
        val contentResolver = applicationContext.contentResolver
        val takeFlags: Int =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        contentResolver.releasePersistableUriPermission(uri, takeFlags)
        adapter.notifyDataSetChanged()
    }

    class MyRecyclerViewAdapter(private val dataList: List<UriPermission>) :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val permissionItem: LinearLayout = view.findViewById(R.id.permissionItem)
            val permissionUri: TextView = view.findViewById(R.id.permissionUri)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_request_permission, parent, false)
            val holder = ViewHolder(view)
            holder.itemView.setOnClickListener {
                val position = holder.adapterPosition
                val uri = dataList[position].uri
                //showBottomDialog(uri)
            }
            return holder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val uri = dataList[position].uri
            holder.permissionUri.text = "/${uri.path?.replace("/tree/primary:", "")}"
        }

        override fun getItemCount() = dataList.size
    }
}