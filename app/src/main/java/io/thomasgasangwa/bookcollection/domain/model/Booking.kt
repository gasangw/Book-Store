package io.thomasgasangwa.bookcollection.domain.model

data class Booking(
    val bookingId: Int,
    val bookId: Int,
    val userId: String,
    val startDate: Long,
    val endDate: Long
)
