package io.thomasgasangwa.bookcollection.presentation.bookings.list_bookings

import BookStoreTheme
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.thomasgasangwa.bookcollection.common.calculateDaysBetweenBookingDates
import io.thomasgasangwa.bookcollection.domain.model.BookWithSingleBooking
import io.thomasgasangwa.bookcollection.presentation.bookings.bookings
import io.thomasgasangwa.bookcollection.presentation.bookings.list_bookings.admin_bookings.AdminsBookingScreen
import io.thomasgasangwa.bookcollection.presentation.bookings.list_bookings.users_bookings.UsersBookingScreen
import io.thomasgasangwa.bookcollection.presentation.view.LocalUserData
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AllBookings(
    modifier: Modifier = Modifier,
) {
    val currentUserInfo = LocalUserData.current

    val bookingViewModel: BookingViewModel = koinViewModel(
        parameters = { parametersOf(currentUserInfo.user?.id) }
    )

    val bookingState by bookingViewModel.state.collectAsStateWithLifecycle()

    val flatBookings: List<BookWithSingleBooking> = bookingState.usersBookings.flatMap { bookWithBooking ->
        bookWithBooking.bookings.map { booking ->
            BookWithSingleBooking(book = bookWithBooking.book, booking = booking)
        }
    }

    val state by bookingViewModel.state.collectAsStateWithLifecycle()

    if (currentUserInfo.user?.email.isNullOrBlank()) {
        if (state.usersBookings.isEmpty()) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No Bookings Found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 300.dp),
                contentPadding = PaddingValues(15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(flatBookings) { booking ->
                    val days = calculateDaysBetweenBookingDates(
                        booking.booking.startDate,
                        booking.booking.endDate

                    )
                    UsersBookingScreen(
                        title = booking.book.title,
                        status = booking.booking.status,
                        bookCoverUrl = booking.book.cover,
                        days = "$days",
                        onCancelBooking = {},
                    )
                }
            }
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            contentPadding = PaddingValues(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(bookings) { booking ->
                AdminsBookingScreen(
                    title = booking.title,
                    userName = booking.userName,
                    days = booking.days
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun AllBookingsPreview() {
    BookStoreTheme {
        AllBookings()
    }
}