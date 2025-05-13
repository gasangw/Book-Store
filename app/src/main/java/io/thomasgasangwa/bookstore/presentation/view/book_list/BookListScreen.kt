package io.thomasgasangwa.bookstore.presentation.view.book_list

import BookStoreTheme
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.thomasgasangwa.bookstore.R
import androidx.compose.ui.unit.dp


data class Book(
    val title: String,
    val author: String,
    @DrawableRes val cover: Int
)

val books: List<Book> = listOf(
    Book(
        title  = "The Alchemist",
        author = "Paulo Holo",
        cover  = R.drawable.trial
    ),
    Book(
        title  = "1984",
        author = "George Orwell",
        cover  = R.drawable.soul
    ),
    Book(
        title  = "To Kill a Mockingbird",
        author = "Harper Lee",
        cover  = R.drawable.million
    ),
    Book(
        title  = "Brave New World",
        author = "Aldous Huxley",
        cover  = R.drawable.trial
    ),
    Book(
        title  = "Sapiens: A Brief History of Humankind",
        author = "Yuval Noah Harari",
        cover  = R.drawable.million
    )
)

@Composable
fun BookListScreen(modifier: Modifier = Modifier) {
    BookListTopBar()
//    LazyVerticalGrid(
//        columns = GridCells.Adaptive(200.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp),
//        modifier = modifier.padding(16.dp)
//    ) {
//        items(books.size) {book -> BookItem(title = books[book].title, author = books[book].author, onClick = {})}
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListTopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {Text(text = "Book Store Library")},
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
private fun BookListScreenPreview() {
    BookStoreTheme {
        BookListScreen()
    }
}