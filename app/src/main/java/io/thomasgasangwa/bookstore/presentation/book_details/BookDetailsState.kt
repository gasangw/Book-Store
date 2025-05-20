package io.thomasgasangwa.bookstore.presentation.book_details

import io.thomasgasangwa.bookstore.domain.model.Book

sealed class BookDetailsState {
    data class Success(val book: Book) : BookDetailsState()
    data class Error(val exception: Exception) : BookDetailsState()
    class Loading(value: Boolean = false) : BookDetailsState()
}

