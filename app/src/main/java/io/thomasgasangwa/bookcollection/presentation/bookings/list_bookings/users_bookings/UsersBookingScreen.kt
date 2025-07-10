package io.thomasgasangwa.bookcollection.presentation.bookings.list_bookings.users_bookings

import BookStoreTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.thomasgasangwa.bookcollection.presentation.bookings.BookingStatus
import io.thomasgasangwa.bookcollection.presentation.bookings.components.BookCover

@Composable
fun UsersBookingScreen(
    modifier: Modifier = Modifier,
    title: String,
    status: BookingStatus?,
    days: String,
    bookCoverUrl: String,
    onCancelBooking: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(120.dp)
            .fillMaxWidth(),
    ) {
        BookCover(
            bookCoverUrl = bookCoverUrl
        )
        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(start = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )

            if(status?.name == "PENDING"){
                Text(
                    text = "Book requested for $days days",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            if(status?.name == "ACCEPTED"){
                Text(
                    text = "Booked for $days days",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.surfaceDim
                )
            }

            if(status?.name == "REJECTED"){
                Text(
                    text = "Booking for $days days was rejected",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Status",
                    textDecoration = TextDecoration.Underline,
                    style = MaterialTheme.typography.bodySmall
                )
                status?.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .background(
                                color = when (status) {
                                    BookingStatus.PENDING -> Color.Gray
                                    BookingStatus.ACCEPTED -> Color(0xFF006400)
                                    BookingStatus.REJECTED -> Color.Red
                                }
                            )
                            .padding(5.dp)
                    )
                }
            }
            when (status) {
                BookingStatus.PENDING -> {
                    Button(
                        onClick = onCancelBooking,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text(text = "Cancel Booking")
                    }
                }

                else -> null
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
private fun UsersBookingScreenPreview() {
    BookStoreTheme {
        UsersBookingScreen(
            title = "Sharks spears reading novels continue reading my guys",
            status = BookingStatus.PENDING,
            days = "4",
            bookCoverUrl = "",
            onCancelBooking = {}
        )
    }
}