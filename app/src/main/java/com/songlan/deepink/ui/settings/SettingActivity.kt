package com.songlan.deepink.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setSupportActionBar(toolbar)
        supportActionBar?.let { it ->
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_back)
            it.title = null
        }

        // 为选项配置点击事件
        testVersion.setOnClickListener(this)
        accountCenter.setOnClickListener(this)
        checkUpdate.setOnClickListener(this)

        signNotify.setOnClickListener(this)
        fontSize.setOnClickListener(this)
        followSystemTheme.setOnClickListener(this)

        chasingMode.setOnClickListener(this)

        shootScreen.setOnClickListener(this)

        useSpace.setOnClickListener(this)
        userGuide.setOnClickListener(this)

        userAgreement.setOnClickListener(this)
        openSourceAgreement.setOnClickListener(this)

        // 为几个开关添加监听事件
        // 签到提醒
        signNotifySwitch.setOnCheckedChangeListener { buttonView, isChecked ->

        }
        // 中等字号
        middleFontSwitch.setOnCheckedChangeListener { buttonView, isChecked ->

        }
        // 系统主题
        followSystemThemeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->

        }
        // 追更模式
        chasingModeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->

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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.testVersion -> {

            }
            R.id.checkUpdate -> {
            }
            R.id.signNotify -> {
                signNotifySwitch.isChecked = !signNotifySwitch.isChecked
            }
            R.id.fontSize -> {
                middleFontSwitch.isChecked = !middleFontSwitch.isChecked
            }
            R.id.followSystemTheme -> {
                followSystemThemeSwitch.isChecked = !followSystemThemeSwitch.isChecked
            }
            R.id.chasingMode -> {
                chasingModeSwitch.isChecked = !chasingModeSwitch.isChecked
            }
            R.id.shootScreen -> {
            }
            R.id.useSpace -> {
            }
            R.id.userGuide -> {
            }
            R.id.userAgreement -> {
            }
            R.id.openSourceAgreement -> {
            }
        }
    }
}