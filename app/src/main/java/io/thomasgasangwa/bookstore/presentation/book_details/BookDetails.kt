package io.thomasgasangwa.bookstore.presentation.book_details

import BookStoreTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.thomasgasangwa.bookstore.R
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.domain.model.toBookParcelableData
import io.thomasgasangwa.bookstore.presentation.favorites.FavoriteViewModel
import io.thomasgasangwa.bookstore.presentation.update_book.BookParcelableData
import io.thomasgasangwa.bookstore.presentation.view.components.BookCover
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun BookDetails(
    modifier: Modifier = Modifier,
    bookId: Int?,
    onEditBook: (BookParcelableData) -> Unit
) {

    val favoriteViewModel: FavoriteViewModel = koinViewModel()

    val bookDetailsViewModel: BookDetailsViewModel =
        koinViewModel(parameters = { parametersOf(bookId) })

    val bookDetailState by bookDetailsViewModel.state.collectAsStateWithLifecycle()


        when (bookDetailState) {
            is BookDetailsState.Loading -> {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            is BookDetailsState.Error -> {
                val bookError = (bookDetailState as BookDetailsState.Error).exception
                Text(text = "Something went wrong $bookError")
            }

            is BookDetailsState.Success -> {
                val book = (bookDetailState as BookDetailsState.Success).book
                BookDetailsContent(
                    book = book,
                    updateFavoriteStatus = {id, isFavorite ->
                        favoriteViewModel.updateFavoriteStatus(id, isFavorite) },
                    onEditBook = onEditBook,
                    modifier = modifier
                )

            }
        }
}

@Composable
fun BookDetailsContent(
    book: Book,
    updateFavoriteStatus: (Int, Boolean) -> Unit,
    onEditBook: (BookParcelableData) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(
                onClick = { updateFavoriteStatus(book.id, !book.isFavorite) },
                modifier = Modifier.scale(1.5f)
            ) {
                Icon(
                    imageVector = if (book.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorite),
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
            IconButton(
                onClick = { onEditBook(book.toBookParcelableData()) },
                modifier = Modifier.scale(1.5f)
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "edit",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(20.dp)
                )
            }
        }
    }
    BookCover(
        modifier = Modifier.height(400.dp),
        bookCoverUrl = book.cover
    )
    Text(
        text = book.title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(10.dp)
    )
    Row(
        modifier = Modifier, verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Pages: ${book.pages},",
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = "Date: ${book.releaseDate},",
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = "Likes: ${book.likes}",
            style = MaterialTheme.typography.displayMedium
        )
    }
    Spacer(modifier = Modifier.height(18.dp))
    Text(
        text = book.description, style = MaterialTheme.typography.labelMedium,
        modifier = Modifier, textAlign = TextAlign.Justify
    )

}


@Preview
@Composable
private fun BookDetailsPreview() {
    BookStoreTheme {
        val sampleBook = Book(
            id = 1,
            title = "Sample Book",
            cover = "sample_cover_url",
            pages = 300,
            releaseDate = "2023-01-01",
            description = "Sample description",
            likes = 100,
            isFavorite = false
        )
            BookDetailsContent(
                book = sampleBook,
                updateFavoriteStatus = {bookId, isFavorite ->},
                onEditBook = {},
                modifier = Modifier
            )
    }
}
