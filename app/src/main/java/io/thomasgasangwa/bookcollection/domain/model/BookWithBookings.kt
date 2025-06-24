package io.thomasgasangwa.bookcollection.domain.model

import androidx.room.Embedded
import androidx.room.Relation

data class BookWithBookings(
    @Embedded val book: Book,
    @Relation(
        parentColumn = "id",
        entityColumn = "bookId"
    )
    val bookings: List<Booking>
)
