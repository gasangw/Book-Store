package io.thomasgasangwa.bookstore.presentation.view.book_list

import io.thomasgasangwa.bookstore.domain.model.Book

data class BookListState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
