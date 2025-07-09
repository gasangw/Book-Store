package io.thomasgasangwa.bookcollection.domain.repository

import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.domain.model.Booking
import io.thomasgasangwa.bookcollection.presentation.bookings.BookingStatus
import kotlinx.coroutines.flow.Flow

interface BookingRepository {
    suspend fun insertBooking(booking: Booking): Result<Unit>

    suspend fun updateBookingStatus(bookingId: Int, status: BookingStatus): Result<Unit>

    suspend fun updateBookingStatusByUserId(
        bookingId: Int,
        userId: String,
        status: BookingStatus
    ): Result<Unit>

    fun getBookingsByUserId(userId: String): Flow<Result<List<Booking>>>

    suspend fun deleteBookingById(bookingId: Int): Result<Unit>

}