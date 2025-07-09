package io.thomasgasangwa.bookcollection.presentation.bookings.list_bookings

import io.thomasgasangwa.bookcollection.domain.model.Booking

data class BookingUiState(
    val isLoading: Boolean = false,
    val usersBookings: List<Booking> = emptyList(),
    val isBookingSuccessful: Boolean = false,
    val errorMessage: String? = null,
    val bookingDialogIsOpen: Boolean = false
)
