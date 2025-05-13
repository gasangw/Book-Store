package io.thomasgasangwa.bookstore.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.thomasgasangwa.bookstore.data.local.dao.BookDao
import io.thomasgasangwa.bookstore.data.local.entity.Book

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
}