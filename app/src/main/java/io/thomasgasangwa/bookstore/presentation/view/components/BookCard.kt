package io.thomasgasangwa.bookstore.presentation.view.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.thomasgasangwa.bookstore.R


@Composable
fun BookCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = MaterialTheme.shapes.large){
        BookCardCoverImage(modifier = Modifier.fillMaxWidth().aspectRatio(ratio = 2f))
        Text(
            text = "Dead are not Dead",
            style = MaterialTheme.typography.titleLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
private fun BookCardCoverImage(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        AsyncImage(
            modifier = modifier.fillMaxSize(),
            model = "",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.placeholder)
        )
    }
}

@Preview
@Composable
private fun BookCardCoverPreview() {
    BookStoreTheme {
        BookCardCoverImage()
    }
}


@Preview
@Composable
private fun BookCardPreview() {
    BookStoreTheme {
        BookCard()
    }
}