package io.thomasgasangwa.bookcollection.data.local.mapper

import io.thomasgasangwa.bookcollection.presentation.bookings.BookingStatus

class Converters {
    fun fromBookingStatus(status: BookingStatus?): String? {
        return status?.name
    }

    fun toBookingStatus(status: String?): BookingStatus? {
        return status?.let { BookingStatus.valueOf(it) }
    }
}