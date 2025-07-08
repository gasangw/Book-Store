package io.thomasgasangwa.bookcollection.presentation.bookings.list_bookings.admin_bookings

import BookStoreTheme
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.thomasgasangwa.bookcollection.presentation.bookings.components.BookCover

@Composable
fun AdminsBookingScreen(
    modifier: Modifier = Modifier,
    title: String,
    userName: String,
    days: String
) {
    Row(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth(),
    ) {
        BookCover(
            bookCoverUrl = ""
        )
        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Booking Requested by $userName for $days days",
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.bodySmall
            )

            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF006400)
                    ),
                ) {
                    Text(text = "Accept")
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                ) {
                    Text(text = "Reject")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AdminsBookingScreenPreview() {
    BookStoreTheme {
        AdminsBookingScreen(
            title = "Sharks spears reading novels",
            userName = "Thomas",
            days = "4"
        )
    }
}