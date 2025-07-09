package io.thomasgasangwa.bookcollection.domain.repository

import io.thomasgasangwa.bookcollection.data.local.entity.BookingEntity
import io.thomasgasangwa.bookcollection.presentation.bookings.BookingStatus
import kotlinx.coroutines.flow.Flow

interface BookingRepository {
    suspend fun insertBooking(booking: BookingEntity)

    suspend fun updateBookingStatus(bookingId: Int, status: BookingStatus)

    suspend fun updateBookingStatusByUserId(bookingId: Int, userId: String, status: BookingStatus)

    fun getBookingsByUserId(userId: String): Flow<List<BookingEntity>>

    suspend fun deleteBookingById(bookingId: Int)

}