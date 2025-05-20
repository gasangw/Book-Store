package io.thomasgasangwa.bookstore.presentation.book_details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BookDetails(modifier: Modifier = Modifier, bookId: Int?) {
//    Column(modifier = modifier) {}
    //Timber.d("Book id $bookId")
    Text(text = "Book Details")
}