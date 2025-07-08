package io.thomasgasangwa.bookcollection.data.local.mapper

import androidx.room.TypeConverter
import io.thomasgasangwa.bookcollection.presentation.bookings.BookingStatus

class Converters {
    @TypeConverter
    fun fromBookingStatus(status: BookingStatus?): String? {
        return status?.name
    }

    @TypeConverter
    fun toBookingStatus(status: String?): BookingStatus? {
        return status?.let { BookingStatus.valueOf(it) }
    }
}