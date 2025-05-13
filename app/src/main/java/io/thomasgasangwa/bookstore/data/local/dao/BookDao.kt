package io.thomasgasangwa.bookstore.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.thomasgasangwa.bookstore.data.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insert(book: Book)

   @Delete
   suspend fun delete(book: Book)

   @Update
   suspend fun update(book: Book)

   @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<Book>>

   @Query("SELECT * FROM books WHERE id = :id")
    fun getBookById(id: Int): Flow<Book>
}