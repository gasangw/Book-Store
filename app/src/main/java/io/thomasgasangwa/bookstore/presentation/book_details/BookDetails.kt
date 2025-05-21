package io.thomasgasangwa.bookstore.presentation.book_details

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.thomasgasangwa.bookstore.R
import io.thomasgasangwa.bookstore.presentation.favorites.FavoriteViewModel
import io.thomasgasangwa.bookstore.presentation.view.components.BookCover
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun BookDetails(modifier: Modifier = Modifier, bookId: Int?, onEditBook: () -> Unit) {

    val favoriteViewModel: FavoriteViewModel = koinViewModel()

    val bookDetailsViewModel: BookDetailsViewModel =
        koinViewModel(parameters = { parametersOf(bookId) })

    val bookDetailState by bookDetailsViewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (bookDetailState) {
            is BookDetailsState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
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
                Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(onClick = {
                        favoriteViewModel.updateFavoriteStatus(
                            id = book.id,
                            isFavorite = !book.isFavorite
                        )
                    }, modifier = modifier.scale(1.5f)) {
                        Icon(
                            imageVector = if (book.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = stringResource(R.string.favorite),
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                    IconButton(onClick = onEditBook, modifier = modifier.scale(1.5f)) {
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
                    modifier = modifier, verticalAlignment = Alignment.CenterVertically,
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
                Spacer(modifier = modifier.height(18.dp))
                Text(
                    text = book.description, style = MaterialTheme.typography.labelMedium,
                    modifier = modifier, textAlign = TextAlign.Justify
                )
            }
        }
    }
}
