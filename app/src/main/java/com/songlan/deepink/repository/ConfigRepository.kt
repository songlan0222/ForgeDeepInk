package com.songlan.deepink.repository

import com.songlan.deepink.AppDatabase
import com.songlan.deepink.MyApplication
import com.songlan.deepink.R
import com.songlan.deepink.model.app.ReadPageMenuItem
import com.songlan.deepink.utils.fire
import kotlinx.coroutines.Dispatchers

object ConfigRepository {

    /**
     * 加载配置文件， 并返回map
     */
//    fun loadActivityPreference() = fire(Dispatchers.IO) {
//        val properties = ConfigUtil.loadActivityPreference()
//        Result.success(properties)
//    }

    /**
     * 将map保存到配置文件中
     */
//    fun saveConfig(activity: Activity, map: Map<String, Any>) = fire(Dispatchers.IO) {
//        ConfigUtil.saveActivityPreference(activity, map)
//        Result.success(Any())
//    }

    /********************** 获取软件配置信息 **************************/
    private val readPageMenuItemDao =
        AppDatabase.getDatabase(MyApplication.context).readPageMenuItemDao()

    /*** ReadPageMenuItem ***/
    private fun insertReadPageMenuItem(item: ReadPageMenuItem) = fire(Dispatchers.IO) {
        val itemId = readPageMenuItemDao.insertItem(item)
        Result.success(itemId)
    }

    private fun deleteReadPageMenuItem(item: ReadPageMenuItem) {
        val deleteItemId = readPageMenuItemDao.deleteItem(item)
        Result.success(deleteItemId)
    }

    private fun loadReadPageMenuItemWithId(itemId: Long) = fire(Dispatchers.IO) {
        val item = readPageMenuItemDao.loadItemWithId(itemId)
        Result.success(item)
    }

    private fun loadReadPageMenuItems(): List<ReadPageMenuItem> {
        val items = readPageMenuItemDao.loadAll()
        return if (items.isEmpty()) {
            initReadPageMenuItems()
            loadReadPageMenuItems()
        } else {
            items
        }
    }

    private fun initReadPageMenuItems() {
        val items = arrayListOf<ReadPageMenuItem>(
            ReadPageMenuItem(R.drawable.ic_rpsetting_theme, "主题", false),
            ReadPageMenuItem(R.drawable.ic_rpsetting_font, "字体", false),
            ReadPageMenuItem(R.drawable.ic_rpsetting_indent, "屏蔽", false),
            ReadPageMenuItem(R.drawable.ic_rpsetting_change_resource, "换源", false),

            ReadPageMenuItem(R.drawable.ic_rpsetting_font_size, "字号", false),
            ReadPageMenuItem(R.drawable.ic_rpsetting_line_margin, "行段", false),
            ReadPageMenuItem(R.drawable.ic_rpsetting_position, "定位", false),
            ReadPageMenuItem(R.drawable.ic_rpsetting_light, "亮度", false),

            ReadPageMenuItem(R.drawable.ic_rpsetting_download, "缓存", false),
            ReadPageMenuItem(R.drawable.ic_rpsetting_page_up_down, "上下翻页", false),
            ReadPageMenuItem(R.drawable.ic_rpsetting_voice_page, "音量键翻页", false),
            ReadPageMenuItem(R.drawable.ic_rpsetting_screen_lock, "屏幕常亮", false),

            ReadPageMenuItem(R.drawable.ic_rpsetting_time_power, "时间电量", true),
            ReadPageMenuItem(R.drawable.ic_rpsetting_title_page_number, "标题页码", true),
            ReadPageMenuItem(R.drawable.ic_rpsetting_indent, "首行缩进", true),
            ReadPageMenuItem(R.drawable.ic_rpsetting_touch, "点击动画", false),

            ReadPageMenuItem(R.drawable.ic_rpsetting_long_press, "文本长按", false),
            ReadPageMenuItem(R.drawable.ic_rpsetting_bookmark, "下拉书签", true),
            ReadPageMenuItem(R.drawable.ic_rpsetting_anchor, "全屏下一页", false),
            ReadPageMenuItem(R.drawable.ic_rpsetting_bold, "字体加粗", false),

            ReadPageMenuItem(R.drawable.ic_rpsetting_background, "背景跟随", false),
            ReadPageMenuItem(R.drawable.ic_rpsetting_statusbar, "状态栏", true),
            ReadPageMenuItem(R.drawable.ic_rpsetting_navbar, "导航栏", true),
            ReadPageMenuItem(R.drawable.ic_rpsetting_iphonex, "异形屏", true),
        )
        val itemIds = readPageMenuItemDao.insertItems(items)
    }

    /**
     * 获取ReadPageMenuItems，如果获取失败，则初始化菜单
     */
    fun getReadPageMenuItems() = fire(Dispatchers.IO) {
        val items = loadReadPageMenuItems()
        Result.success(items)
    }
}