package io.thomasgasangwa.bookstore.presentation.view.book_list

import BookStoreTheme
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.thomasgasangwa.bookstore.R
import io.thomasgasangwa.bookstore.presentation.view.components.BookItem
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
fun DisplayAllBooks(modifier: Modifier = Modifier) {
//    val myViewModel: BookListViewModel = koinViewModel()
//    val allBooks by myViewModel.state.collectAsState()

   // Log.d("TAG", "DisplayAllBooks: ${allBooks.books}")

    Text(text = "Available Books", modifier = modifier, style = MaterialTheme.typography.displayLarge)
    Spacer(modifier = modifier.height(16.dp))
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(16.dp)
    ) {
        items(books.size) {book -> BookItem(title = books[book].title, author = books[book].author, cover = books[book].cover, onClick = {})}
    }
}

@PreviewLightDark
@Composable
private fun DisplayAllBooksPreview() {
    BookStoreTheme {
        DisplayAllBooks()
    }
}