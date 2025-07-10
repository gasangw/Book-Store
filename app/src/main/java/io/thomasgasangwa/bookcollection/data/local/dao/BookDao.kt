package io.thomasgasangwa.bookcollection.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import io.thomasgasangwa.bookcollection.data.local.entity.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: BookEntity)

    @Update
    suspend fun update(book: BookEntity)

    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<BookEntity>>

//    @Transaction
//    @Query("""SELECT * FROM books WHERE id NOT IN (SELECT bookId FROM bookings WHERE status IS NULL)""")
//    fun getAvailableBooks(): Flow<List<BookEntity>>

    @Query("""SELECT * FROM books WHERE id IN (SELECT bookId FROM bookings WHERE userId =:userId)""")
    fun getUserBookingBooks(userId: String): Flow<List<BookEntity>>

    @Transaction
    @Query("""SELECT * FROM books WHERE id IN (SELECT bookId FROM bookings WHERE status IS NOT NULL AND status = 'PENDING')""")
    fun getBooksWithBookings(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :id")
    fun getBookById(id: Int): Flow<BookEntity>

    @Query("DELETE FROM books WHERE id = :id")
    suspend fun deleteBookById(id: Int)

    @Query("UPDATE books SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM books WHERE isFavorite = 1")
    fun getFavoriteBooks(): Flow<List<BookEntity>>

    @Query("UPDATE books SET likes = :likes  WHERE id = :id")
    suspend fun updateLikes(id: Int, likes: Int)

}