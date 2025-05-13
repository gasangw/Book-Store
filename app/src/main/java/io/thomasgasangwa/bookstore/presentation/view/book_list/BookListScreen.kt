package io.thomasgasangwa.bookstore.presentation.view.book_list

import BookStoreTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import io.thomasgasangwa.bookstore.domain.model.Book
import io.thomasgasangwa.bookstore.presentation.view.components.BookCard



@Composable
fun BookListScreen(modifier: Modifier = Modifier) {

    val myBook = Book(
        id = 1,
        title = "The Alchemist",
        author = "Paulo Coelho",
        bookCoverUrl = "https://images.unsplash.com/photo-1491841573634-28140fc7ced7?fm=jpg&q=60&w=3000&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTh8fGJvb2t8ZW58MHx8MHx8fDA%3D",
        contentUrl = "",
        content = null
    )

    Column(modifier = modifier) {
        BookListTopBar()
        LazyVerticalGrid(
         columns = GridCells.Adaptive(minSize = 300.dp),
            contentPadding = PaddingValues(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(15) {
                BookCard(book = myBook)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListTopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {Text(text = "Book Store Library")},
        windowInsets = WindowInsets(0),
        modifier = modifier
    )
}

@PreviewScreenSizes
@Composable
private fun BookListScreenPreview() {
    BookStoreTheme {
        BookListScreen()
    }
}