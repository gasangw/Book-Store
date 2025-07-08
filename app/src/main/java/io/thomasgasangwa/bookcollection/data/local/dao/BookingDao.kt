package io.thomasgasangwa.bookcollection.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.thomasgasangwa.bookcollection.data.local.entity.BookingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    @Upsert
    suspend fun insertBooking(booking: BookingEntity)

    @Query("SELECT * FROM bookings WHERE userId = :userId")
    fun getBookingsByUserId(userId: String): Flow<List<BookingEntity>>

    @Query("DELETE FROM bookings WHERE bookingId = :bookingId")
    suspend fun deleteBookingById(bookingId: Int)
}