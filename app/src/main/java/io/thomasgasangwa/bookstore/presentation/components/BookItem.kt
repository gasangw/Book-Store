package io.thomasgasangwa.bookstore.presentation.components

import BookStoreTheme
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.thomasgasangwa.bookstore.R

@Composable
fun BookItem(modifier: Modifier = Modifier, title: String, author: String, @DrawableRes cover: Int, onClick: () -> Unit) {
    Card(modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        onClick = onClick
        ) {
        Column(modifier = modifier
        ) {
            Image(painter = painterResource(cover),
                contentDescription = title,
                modifier = modifier.clip(shape = MaterialTheme.shapes.extraSmall),
                contentScale = ContentScale.Fit
            )
            Text(text = title, style = MaterialTheme.typography.displayLarge)
            Text(text = author, style = MaterialTheme.typography.displayMedium)

        }
    }
}

@PreviewLightDark
@Composable
private fun BookItemPreview() {
    BookStoreTheme {
        BookItem(title = "The Alchemist", author = "Paulo holo", cover = R.drawable.trial, onClick = {})
    }
}