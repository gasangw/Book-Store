package io.thomasgasangwa.bookstore.presentation.book_list.components

import BookStoreTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BookStatusDisplay(modifier: Modifier = Modifier, status: BookingStatus) {
    Text(
        text = status.name.lowercase(),
        modifier
            .clip(shape = MaterialTheme.shapes.medium)
            .background(color = if(status == BookingStatus.AVAILABLE) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        style = MaterialTheme.typography.labelMedium,
        color = if(status == BookingStatus.AVAILABLE) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.error
    )
}

@Preview
@Composable
private fun BookStatusDisplayPreview() {
    BookStoreTheme{
        BookStatusDisplay(status = BookingStatus.AVAILABLE)

    }
}