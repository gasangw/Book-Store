package io.thomasgasangwa.bookstore.presentation.book_list.components

import BookStoreTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import io.thomasgasangwa.bookstore.R
import io.thomasgasangwa.bookstore.domain.model.Book


@Composable
fun BookCard(
    modifier: Modifier = Modifier,
    title: String,
    bookCoverUrl: String
) {
    Card(modifier = modifier, shape = MaterialTheme.shapes.large){
        BookCardCoverImage(
            modifier = Modifier.fillMaxWidth().aspectRatio(ratio = 2f),
            bookCoverUrl = bookCoverUrl
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
private fun BookCardCoverImage(modifier: Modifier = Modifier, bookCoverUrl: String) {
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

@Preview
@Composable
private fun BookCardPreview() {
    BookStoreTheme {
        val myBook = Book(
            id = 0,
          title = "thomas",
          releaseDate = "2025",
        description = "hello this is my new book",
        pages = 223,
        cover = "",
        likes = 0,
        )
        BookCard(bookCoverUrl = "", title = myBook.title)
    }
}