package io.thomasgasangwa.bookstore.presentation.book_list.components

import BookStoreTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.thomasgasangwa.bookstore.R
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.presentation.view.components.BookCover


@Composable
fun BookCard(
    modifier: Modifier = Modifier,
    id: Int,
    title: String,
    bookCoverUrl: String,
    deleteBook: (Int) -> Unit,
    likes: Int,
    pages: Int,
    description: String,
    onBookClicked: (Int) -> Unit,
    updateLikes: ((Int, Int, Boolean) -> Unit)? = null,
) {
    val context = LocalContext.current
    var isLiked: Boolean by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        onClick = { onBookClicked(id) }
    ) {
        BookCover(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 2f),
            bookCoverUrl = bookCoverUrl
        )
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(10.dp)
        )
        Row {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    isLiked = !isLiked
                    updateLikes?.invoke(id, likes, isLiked)
                }) {
                    Icon(
                        imageVector = if (isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                        contentDescription = stringResource(
                            R.string.like
                        )
                    )
                }
                Text(text = likes.toString(), style = MaterialTheme.typography.displayMedium)
            }
            IconButton(onClick = {
                shareBook(
                    context = context,
                    subject = "Check out this book: $title",
                    summary = description,
                    pages = pages
                )
            }) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "share"
                )
            }
            IconButton(onClick = { deleteBook(id) }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
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
            isFavorite = false
        )
        BookCard(
            bookCoverUrl = "",
            title = myBook.title,
            id = myBook.id,
            deleteBook = {},
            likes = myBook.likes,
            pages = myBook.pages,
            description = myBook.description,
            onBookClicked = {},
            updateLikes = { _, _, _ -> }
        )
    }
}