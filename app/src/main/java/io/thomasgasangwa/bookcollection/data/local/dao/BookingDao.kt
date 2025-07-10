package io.thomasgasangwa.bookcollection.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.thomasgasangwa.bookcollection.data.local.entity.BookingEntity
import io.thomasgasangwa.bookcollection.domain.model.BookWithBookings
import io.thomasgasangwa.bookcollection.presentation.bookings.BookingStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingEntity)

    @Query("UPDATE bookings SET status = :status WHERE bookingId = :bookingId")
    suspend fun updateBookingStatus(bookingId: Int, status: BookingStatus)

    @Query("UPDATE bookings SET status = :status WHERE bookingId = :bookingId AND userId = :userId")
    suspend fun updateBookingStatusByUserId(bookingId: Int, userId: String, status: BookingStatus)

    @Transaction
    @Query("""SELECT * FROM books WHERE id IN (SELECT bookId FROM bookings WHERE userId =:userId) """)
    fun getBooksWithUserBookings(userId: String): Flow<List<BookWithBookings>>
    
    @Query("DELETE FROM bookings WHERE bookingId = :bookingId")
    suspend fun deleteBookingById(bookingId: Int)
}