package io.thomasgasangwa.bookstore.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.thomasgasangwa.bookstore.presentation.book_list.components.BookCard
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import io.thomasgasangwa.bookstore.R


@Composable
fun BookListScreen(
    modifier: Modifier = Modifier
) {
    val bookViewModel: BookListViewModel = koinViewModel()
     val booksState by bookViewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { BookListTopBar() },
        floatingActionButton = {
            LargeFloatingActionButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_book_icon),
                    modifier = Modifier.size(40.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier
    ){ innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            when(booksState){
                is BookListState.Loading -> {
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                    }
                }
                is BookListState.Success -> {
                    val books = (booksState as BookListState.Success).books
                    if(books.isEmpty()) {
                        Text(
                            text = stringResource(R.string.no_books_found),
                            modifier = modifier.align(Alignment.CenterHorizontally),
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
                            items(books) {book ->
                                BookCard(title = book.title, bookCoverUrl = book.bookCoverUrl)
                            }
                        }
                    }

                }
                is BookListState.Error -> {
                    val exception = (booksState as BookListState.Error).exception
                    Text(
                        text = "Error: ${exception.message}",
                        modifier = Modifier.background(color = MaterialTheme.colorScheme.error)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListTopBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(R.string.booklistscreen_title))},
        modifier = modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    )
}
