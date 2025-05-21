package io.thomasgasangwa.bookstore.presentation.update_book

import io.thomasgasangwa.bookstore.domain.model.Book

sealed class UpdateBookState {
    data class Success(val book: Book) : UpdateBookState()
    data class Error(val exception: Exception) : UpdateBookState()
}
