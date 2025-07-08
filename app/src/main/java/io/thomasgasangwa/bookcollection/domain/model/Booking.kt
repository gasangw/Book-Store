package io.thomasgasangwa.bookcollection.domain.model

import io.thomasgasangwa.bookcollection.presentation.bookings.BookingStatus

data class Booking(
    val bookingId: Int,
    val bookId: Int,
    val userId: String,
    val startDate: Long,
    val endDate: Long,
    val status: BookingStatus? = null
)
