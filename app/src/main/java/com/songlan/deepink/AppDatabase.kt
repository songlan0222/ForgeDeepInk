package com.songlan.deepink

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.songlan.deepink.dao.app.ReadPageMenuItemDao
import com.songlan.deepink.dao.data.BookDao
import com.songlan.deepink.dao.data.BookshelfDao
import com.songlan.deepink.dao.data.ChapterDao
import com.songlan.deepink.model.app.ReadPageMenuItem
import com.songlan.deepink.model.data.Book
import com.songlan.deepink.model.data.Bookshelf
import com.songlan.deepink.model.data.Chapter

@Database(
    version = 2,
    entities = [Book::class, Bookshelf::class, Chapter::class, ReadPageMenuItem::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun bookshelfDao(): BookshelfDao
    abstract fun chapterDao(): ChapterDao
    abstract fun readPageMenuItemDao(): ReadPageMenuItemDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            )
                .fallbackToDestructiveMigration()
                //.allowMainThreadQueries()
                .build().apply {
                    instance = this
                }
        }
    }
}