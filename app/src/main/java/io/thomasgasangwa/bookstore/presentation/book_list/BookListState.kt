package io.thomasgasangwa.bookstore.presentation.book_list

import io.thomasgasangwa.bookstore.domain.model.Book

data class BookListState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val error: String = ""
)
