package io.thomasgasangwa.bookcollection.presentation.view.components

import BookStoreTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import io.thomasgasangwa.bookcollection.R
import io.thomasgasangwa.bookcollection.presentation.book_list.components.BookStatusDisplay
import io.thomasgasangwa.bookcollection.presentation.bookings.BookingStatus

@Composable
fun BookCover(
    modifier: Modifier = Modifier,
    bookCoverUrl: String,
    bookingStatus: BookingStatus?
) {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(bookCoverUrl)
        .crossfade(true)
        .build()


    Box(modifier = modifier) {
        AsyncImage(
            modifier = modifier,
            model = imageRequest,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.placeholder)
        )
        BookStatusDisplay(
            status = bookingStatus?.name ?: "available",
            modifier = Modifier
                .align(
                    alignment = Alignment.TopEnd
                )
                .padding(10.dp)

        )
    }
}

@Preview
@Composable
private fun BookCoverPreview() {
    BookStoreTheme {
        BookCover(
            bookCoverUrl = "",
            bookingStatus = BookingStatus.PENDING
        )
    }
}