package io.thomasgasangwa.bookcollection.presentation.bookings.components

import BookStoreTheme
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
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

@Composable
fun BookCover(modifier: Modifier = Modifier, bookCoverUrl: String) {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(bookCoverUrl)
        .crossfade(true)
        .build()

    AsyncImage(
        modifier = Modifier.size(100.dp),
        model = imageRequest,
        contentDescription = "book cover",
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.placeholder),
        error = painterResource(R.drawable.placeholder)
    )
}

@Preview
@Composable
private fun BookCoverPreview() {
    BookStoreTheme {
        BookCover(
            bookCoverUrl = ""
        )
    }
}