package io.thomasgasangwa.bookcollection.domain.model

import androidx.room.Embedded
import androidx.room.Relation
import io.thomasgasangwa.bookcollection.data.local.entity.BookEntity
import io.thomasgasangwa.bookcollection.data.local.entity.BookingEntity

data class BookWithBookings(
    @Embedded val book: BookEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "bookId"
    )
    val bookings: List<BookingEntity>
)

data class BookWithBooking(
    val book: Book,
    val bookings: List<Booking>
)