package com.songlan.deepink.ui.local

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.songlan.deepink.AppProfiles
import com.songlan.deepink.MyApplication.Companion.context
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.activity_request_storage_permissive.*


class RequestStoragePermissive : AppCompatActivity() {
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

    fun queryPermission() {
        val contentResolver = applicationContext.contentResolver
        contentResolver.persistedUriPermissions.forEach {
            it.uri.toString()
        }
    }

    private fun takePermission(uri: Uri) {
        val contentResolver = applicationContext.contentResolver
        val takeFlags: Int =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        contentResolver.takePersistableUriPermission(uri, takeFlags)
        AppProfiles.saveToProfile(uri.toString(), uri)
    }

    fun releasePermission(uri: Uri) {
        val contentResolver = applicationContext.contentResolver
        val takeFlags: Int =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        contentResolver.releasePersistableUriPermission(uri, takeFlags)
    }
}