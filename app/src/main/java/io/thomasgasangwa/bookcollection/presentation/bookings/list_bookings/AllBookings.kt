package io.thomasgasangwa.bookcollection.presentation.bookings.list_bookings

import BookStoreTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AllBookings(modifier: Modifier = Modifier) {
    Text(text = "bookings")
}

@Preview
@Composable
private fun AllBookingsPreview() {
    BookStoreTheme {
        AllBookings()
    }
}