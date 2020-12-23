package com.songlan.deepink.ui.local

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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


            finish()
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
}