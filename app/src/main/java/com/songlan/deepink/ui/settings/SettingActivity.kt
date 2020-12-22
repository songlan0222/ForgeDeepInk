package com.songlan.deepink.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.songlan.deepink.AppProfiles
import com.songlan.deepink.AppProfiles.saveToProfile
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
            saveToProfile(AppProfiles.SIGN_NOTIFY, isChecked)
        }
        // 中等字号
        middleFontSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            saveToProfile(AppProfiles.MIDDLE_FONT_SIZE, isChecked)
        }
        // 系统主题
        followSystemThemeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            saveToProfile(AppProfiles.FOLLOW_SYSTEM_THEME, isChecked)
        }
        // 追更模式
        chasingModeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            saveToProfile(AppProfiles.CHASING_MODE, isChecked)
        }
        // 检查更新间隔
        updateGroup.setOnCheckedChangeListener { group, checkedId ->
            var checkedItem = 0
            when (checkedId) {
                R.id.updateInStartApp -> {
                    checkedItem = 0
                }
                R.id.updateIn30Min -> {
                    checkedItem = 1
                }
                R.id.updateIn60Min -> {
                    checkedItem = 2
                }
            }
            saveToProfile(AppProfiles.CHECK_UPDATE, checkedItem)
        }

        // 配置信息展示
        when (intent.getIntExtra(AppProfiles.CHECK_UPDATE, 0)) {
            0 -> updateInStartApp.isChecked = true
            1 -> updateIn30Min.isChecked = true
            2 -> updateIn60Min.isChecked = true
        }
        signNotifySwitch.isChecked = intent.getBooleanExtra(AppProfiles.SIGN_NOTIFY, false)
        middleFontSwitch.isChecked = intent.getBooleanExtra(AppProfiles.MIDDLE_FONT_SIZE, false)
        followSystemThemeSwitch.isChecked =
            intent.getBooleanExtra(AppProfiles.FOLLOW_SYSTEM_THEME, false)
        chasingModeSwitch.isChecked = intent.getBooleanExtra(AppProfiles.CHASING_MODE, false)

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