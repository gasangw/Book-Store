package io.thomasgasangwa.bookstore.presentation.view.components

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
import io.thomasgasangwa.bookstore.R
import io.thomasgasangwa.bookstore.presentation.book_list.components.BookStatusDisplay
import io.thomasgasangwa.bookstore.presentation.book_list.components.BookingStatus

@Composable
fun BookCover(modifier: Modifier = Modifier, bookCoverUrl: String) {
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
            status = BookingStatus.AVAILABLE,
            modifier = Modifier.align(
                alignment = Alignment.TopEnd).padding(10.dp)

        )
    }
}

@Preview
@Composable
private fun BookCoverPreview() {
    BookStoreTheme{
        BookCover(bookCoverUrl = "")
    }
}