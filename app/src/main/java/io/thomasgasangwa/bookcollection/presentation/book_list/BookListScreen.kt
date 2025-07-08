package io.thomasgasangwa.bookcollection.presentation.book_list

import BookStoreTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import io.thomasgasangwa.bookcollection.R
import io.thomasgasangwa.bookcollection.common.state.UserState
import io.thomasgasangwa.bookcollection.domain.model.Book
import io.thomasgasangwa.bookcollection.presentation.book_list.components.BookCard
import io.thomasgasangwa.bookcollection.presentation.view.LocalUserData
import org.koin.androidx.compose.koinViewModel


@Composable
fun BookListScreen(
    modifier: Modifier = Modifier,
    onAddBookButtonClicked: () -> Unit,
    onBookClicked: (Int) -> Unit
) {
    val bookViewModel: BookListViewModel = koinViewModel()
    val booksState by bookViewModel.state.collectAsStateWithLifecycle()
    val currentUserInfo = LocalUserData.current

    BookListDisplay(
        currentUserInfo = currentUserInfo,
        onAddBookButtonClicked = onAddBookButtonClicked,
        onBookClicked = { it -> onBookClicked(it) },
        booksState = booksState,
        getAllRemoteBooks = { bookViewModel.getAllRemoteBooks() },
        deleteBook = { it -> bookViewModel.deleteBook(it) },
        updateLikes = { id, likes, isLiked -> bookViewModel.updateLikes(id, likes, isLiked) },
        modifier = modifier
    )
}

@Composable
fun BookListDisplay(
    currentUserInfo: UserState,
    onAddBookButtonClicked: () -> Unit,
    onBookClicked: (Int) -> Unit,
    booksState: BookListState,
    getAllRemoteBooks: () -> Unit,
    deleteBook: (Int) -> Unit,
    updateLikes: (Int, Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            if (currentUserInfo.user?.email?.isNotEmpty() == true) {
                LargeFloatingActionButton(onClick = onAddBookButtonClicked) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_book_icon),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier

    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            when (booksState) {
                is BookListState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(100.dp),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }

                is BookListState.Success -> {
                    val books = booksState.books
                    if (books.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(onClick = { getAllRemoteBooks() }) {
                                Text(
                                    text = stringResource(R.string.fetch_books),
                                    textAlign = TextAlign.Center,
                                    modifier = modifier.padding(6.dp),
                                    style = MaterialTheme.typography.displayMedium,
                                )
                            }
                        }
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
                                    },
                                    updateLikes = { id, likes, isLiked ->
                                        updateLikes(id, likes, isLiked)

                                    }
                                )
                            }
                        }
                    }

                }

                is BookListState.Error -> {
                    Text(
                        text = "Error occurred while loading the books",
                        modifier = Modifier.background(color = MaterialTheme.colorScheme.error)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun BookListScreenPreview() {
    val bookList = emptyList<Book>()

    BookStoreTheme {
        BookListDisplay(
            currentUserInfo = UserState(),
            onAddBookButtonClicked = {},
            onBookClicked = { _ -> },
            booksState = BookListState.Success(bookList),
            deleteBook = { _ -> },
            updateLikes = { _, _, _ -> },
            getAllRemoteBooks = {},
            modifier = Modifier
        )
    }
}
