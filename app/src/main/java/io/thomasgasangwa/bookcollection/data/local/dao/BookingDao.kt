package io.thomasgasangwa.bookcollection.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.thomasgasangwa.bookcollection.domain.model.Booking
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    @Upsert
    suspend fun insertBooking(booking: Booking)

    @Query("SELECT * FROM bookings")
    fun getAllBookings(): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE userId = :userId")
    fun getBookingsByUserId(userId: String): Flow<List<Booking>>

    @Query("DELETE FROM bookings WHERE bookingId = :bookingId")
    suspend fun deleteBookingById(bookingId: Int)
}