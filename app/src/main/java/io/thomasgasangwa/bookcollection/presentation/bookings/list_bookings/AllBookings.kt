package io.thomasgasangwa.bookcollection.presentation.bookings.list_bookings

import BookStoreTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.thomasgasangwa.bookcollection.presentation.auth.sign_in.SignInUiState
import io.thomasgasangwa.bookcollection.presentation.bookings.bookings
import io.thomasgasangwa.bookcollection.presentation.bookings.userBookings

@Composable
fun AllBookings(
    modifier: Modifier = Modifier,
    state: SignInUiState,
) {

    if (state.user?.email.isNullOrBlank()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            contentPadding = PaddingValues(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(userBookings) { booking ->
                UsersBookingScreen(
                    title = booking.title,
                    status = booking.status,
                    onCancelBooking = {},
                )
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
        AllBookings(
            state = SignInUiState()
        )
    }
}