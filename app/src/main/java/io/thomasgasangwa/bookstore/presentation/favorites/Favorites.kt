package io.thomasgasangwa.bookstore.presentation.favorites

import BookStoreTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.thomasgasangwa.bookstore.R
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.presentation.view_models.BookListViewModel
import io.thomasgasangwa.bookstore.presentation.view_models.components.BookCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun Favorites(modifier: Modifier = Modifier, onBookClicked: (Int) -> Unit) {
    val favoriteViewModel: FavoriteViewModel = koinViewModel()
    val bookViewModel: BookListViewModel = koinViewModel()
    val favoriteState by favoriteViewModel.state.collectAsStateWithLifecycle()

    FavoriteBooks(
        favoriteState = favoriteState,
        deleteBook = { it -> bookViewModel.deleteBook(it) },
        onBookClicked = { it -> onBookClicked(it) },
        modifier = modifier
    )
}

@Composable
fun FavoriteBooks(
    favoriteState: FavoriteBookState,
    deleteBook: (Int) -> Unit,
    onBookClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier) {
        when (favoriteState) {
            is FavoriteBookState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            is FavoriteBookState.Success -> {
                val books = favoriteState.books
                if (books.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_favorite_books),
                        textAlign = TextAlign.Center,
                        modifier = modifier.padding(10.dp),
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 300.dp),
                        contentPadding = PaddingValues(15.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        items(books) { book ->
                            BookCard(
                                title = book.title,
                                bookCoverUrl = book.cover,
                                id = book.id,
                                deleteBook = { id ->
                                    deleteBook(id)
                                },
                                likes = book.likes,
                                description = book.description,
                                onBookClicked = { id ->
                                    onBookClicked(id)
                                }
                            )
                        }
                    }
                }

            }

            is FavoriteBookState.Error -> {
                Text(
                    text = "Error occurred while loading favorite books",
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.error)
                )
            }
        }
    }
}

@Preview
@Composable
private fun FavoritePreviewScreen() {
    val favoriteBookList = listOf(
        Book(
            id = 1,
            title = "Sample Book",
            cover = "sample_cover_url",
            pages = 300,
            releaseDate = "2023-01-01",
            description = "Sample description",
            likes = 20,
            isFavorite = true
        )
    )
    BookStoreTheme {
        FavoriteBooks(
            onBookClicked = { _ -> },
            favoriteState = FavoriteBookState.Success(favoriteBookList),
            deleteBook = { _ -> },
            modifier = Modifier
        )
    }
}