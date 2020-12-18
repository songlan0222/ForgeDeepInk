package com.songlan.deepink

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.songlan.deepink.dao.BookDao
import com.songlan.deepink.dao.BookshelfDao
import com.songlan.deepink.model.Book
import com.songlan.deepink.model.Bookshelf
import com.songlan.deepink.model.Chapter

@Database(
    version = 1,
    entities = [Book::class, Bookshelf::class, Chapter::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun bookshelfDao(): BookshelfDao

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
                .allowMainThreadQueries()
                .build().apply {
                    instance = this
                }
        }
    }
}