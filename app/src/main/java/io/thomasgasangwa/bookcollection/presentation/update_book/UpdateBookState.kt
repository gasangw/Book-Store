package io.thomasgasangwa.bookcollection.presentation.update_book

import io.thomasgasangwa.bookcollection.domain.model.Book

sealed class UpdateBookState {
    data class Success(val book: Book) : UpdateBookState()
    data class Error(val exception: Exception) : UpdateBookState()
}
