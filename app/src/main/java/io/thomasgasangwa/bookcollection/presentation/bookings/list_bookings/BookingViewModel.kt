package io.thomasgasangwa.bookcollection.presentation.bookings.list_bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.thomasgasangwa.bookcollection.common.Result
import io.thomasgasangwa.bookcollection.domain.model.Booking
import io.thomasgasangwa.bookcollection.domain.repository.BookingRepository
import io.thomasgasangwa.bookcollection.presentation.bookings.BookingStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class BookingViewModel(
    private val bookingRepository: BookingRepository,
    private val userId: String
) : ViewModel() {

    private val _state = MutableStateFlow(BookingUiState())
    val state: StateFlow<BookingUiState> = _state.asStateFlow()

    init {
        getBookingsByUserID()
    }

    fun getBookingsByUserID() {
        try {
            _state.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                bookingRepository.getBooksWithUserBookings(userId).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _state.update {
                                Timber.e("results ${result.value}")
                                it.copy(
                                    usersBookings = result.value.flatMap { it.bookings },
                                    isLoading = false
                                )
                            }
                        }

                        is Result.Failure -> {
                            _state.update {
                                it.copy(
                                    errorMessage = result.exception.message,
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            _state.update { it.copy(errorMessage = e.message) }
        }
    }


    fun insertBooking(booking: Booking) {
        try {
            _state.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                bookingRepository.insertBooking(booking)
                _state.update { it.copy(isBookingSuccessful = true) }
            }
        } catch (e: Exception) {
            _state.update { it.copy(errorMessage = e.message) }
        }
    }

    fun updateBookingStatus(bookingId: Int, status: BookingStatus) {
        try {
            _state.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                bookingRepository.updateBookingStatus(bookingId, status)
                _state.update { it.copy(isBookingSuccessful = true) }
            }
        } catch (e: Exception) {
            _state.update { it.copy(errorMessage = e.message) }
        }
    }

    fun updateBookingStatusByUserId(bookingId: Int, userId: String, status: BookingStatus) {
        try {
            _state.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                bookingRepository.updateBookingStatusByUserId(bookingId, userId, status)
                _state.update { it.copy(isBookingSuccessful = true) }
            }
        } catch (e: Exception) {
            _state.update { it.copy(errorMessage = e.message) }
        }
    }

    fun deleteBookingById(bookingId: Int) {
        try {
            _state.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                bookingRepository.deleteBookingById(bookingId)
                _state.update { it.copy(isBookingSuccessful = true) }
            }
        } catch (e: Exception) {
            _state.update { it.copy(errorMessage = e.message) }
        }
    }

}