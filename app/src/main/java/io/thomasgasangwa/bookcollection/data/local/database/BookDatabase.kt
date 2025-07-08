package io.thomasgasangwa.bookcollection.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.thomasgasangwa.bookcollection.data.local.dao.BookDao
import io.thomasgasangwa.bookcollection.data.local.dao.BookingDao
import io.thomasgasangwa.bookcollection.data.local.entity.BookEntity
import io.thomasgasangwa.bookcollection.data.local.entity.BookingEntity
import io.thomasgasangwa.bookcollection.data.local.mapper.Converters


@Database(entities = [BookEntity::class, BookingEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
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



