package io.thomasgasangwa.bookstore.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.thomasgasangwa.bookstore.data.local.dao.BookDao
import io.thomasgasangwa.bookstore.data.local.entity.Book

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var Instance: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BookDatabase::class.java, "book_database")
                    .fallbackToDestructiveMigrationOnDowngrade(true)
                    .build()
                    .also { Instance = it }
            }
        }
    }

}