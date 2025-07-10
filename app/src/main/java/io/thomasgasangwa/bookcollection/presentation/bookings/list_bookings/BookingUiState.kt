package io.thomasgasangwa.bookcollection.presentation.bookings.list_bookings

import io.thomasgasangwa.bookcollection.domain.model.BookWithBooking

data class BookingUiState(
    val isLoading: Boolean = false,
    val usersBookings: List<BookWithBooking> = emptyList(),
    val isBookingSuccessful: Boolean = false,
    val errorMessage: String? = null,
    val bookingDialogIsOpen: Boolean = false
)
