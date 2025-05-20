package io.thomasgasangwa.bookstore.presentation.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import io.thomasgasangwa.bookstore.R

@Composable
fun BookCover(modifier: Modifier = Modifier, bookCoverUrl: String) {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(bookCoverUrl)
        .crossfade(true)
        .build()


    Box(modifier = modifier) {
        AsyncImage(
            modifier = modifier.fillMaxSize(),
            model = imageRequest,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.placeholder)
        )
    }
}