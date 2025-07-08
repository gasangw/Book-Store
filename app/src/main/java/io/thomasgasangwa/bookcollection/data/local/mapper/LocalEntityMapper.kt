package io.thomasgasangwa.bookcollection.data.local.mapper

import io.thomasgasangwa.bookcollection.data.local.entity.BookEntity
import io.thomasgasangwa.bookcollection.data.local.entity.BookingEntity
import io.thomasgasangwa.bookcollection.domain.model.Book
import io.thomasgasangwa.bookcollection.domain.model.Booking

fun BookEntity.toBook() = Book(
    id = id,
    title = title,
    releaseDate = releaseDate,
    description = description,
    pages = pages,
    cover = cover,
    likes = likes,
    isFavorite = isFavorite
)

fun List<BookEntity>.toBookList() = map { it.toBook() }

fun Book.toBookEntity() = BookEntity(
    id = id,
    title = title,
    releaseDate = releaseDate,
    description = description,
    pages = pages,
    cover = cover,
    likes = likes,
    isFavorite = isFavorite
)


fun BookingEntity.toBooking() = Booking(
    bookingId = bookingId,
    bookId = bookId,
    userId = userId,
    startDate = startDate,
    endDate = endDate
)

fun List<BookingEntity>.toBookingList() = map { it.toBooking() }

fun Booking.toBookingEntity() = BookingEntity(
    bookingId = bookingId,
    bookId = bookId,
    userId = userId,
    startDate = startDate,
    endDate = endDate
)
