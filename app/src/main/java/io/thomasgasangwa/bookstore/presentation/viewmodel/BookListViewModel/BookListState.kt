package io.thomasgasangwa.bookstore.presentation.viewmodel.BookListViewModel

import io.thomasgasangwa.bookstore.domain.model.Book

data class BookListState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val error: String = ""
)