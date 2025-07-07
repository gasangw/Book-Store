package io.thomasgasangwa.bookcollection.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.thomasgasangwa.bookcollection.data.local.dao.BookDao
import io.thomasgasangwa.bookcollection.data.local.dao.BookingDao
import io.thomasgasangwa.bookcollection.data.local.entity.BookEntity
import io.thomasgasangwa.bookcollection.data.local.entity.BookingEntity


@Database(entities = [BookEntity::class, BookingEntity::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun bookingDao(): BookingDao

    companion object {

        @Volatile
        var Instance: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BookDatabase::class.java, "book_database")
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also { Instance = it }
            }
        }

    }
}



