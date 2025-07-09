package io.thomasgasangwa.bookcollection.presentation.bookings.list_bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookcollection.domain.model.Booking
import io.thomasgasangwa.bookcollection.domain.repository.BookingRepository
import io.thomasgasangwa.bookcollection.presentation.bookings.BookingStatus
import kotlinx.coroutines.launch

class BookingViewModel(
    private val bookingRepository: BookingRepository
) : ViewModel() {

    fun insertBooking(booking: Booking) {
        viewModelScope.launch {
            bookingRepository.insertBooking(booking)
        }
    }

    fun updateBookingStatus(bookingId: Int, status: BookingStatus) {
        viewModelScope.launch {
            bookingRepository.updateBookingStatus(bookingId, status)
        }
    }

    fun updateBookingStatusByUserId(bookingId: Int, userId: String, status: BookingStatus) {
        viewModelScope.launch {
            bookingRepository.updateBookingStatusByUserId(bookingId, userId, status)
        }
    }

    fun deleteBookingById(bookingId: Int) {
        viewModelScope.launch {
            bookingRepository.deleteBookingById(bookingId)
        }
    }

}