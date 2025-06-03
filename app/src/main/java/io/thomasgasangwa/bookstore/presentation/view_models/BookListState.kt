package io.thomasgasangwa.bookstore.presentation.view_models

import io.thomasgasangwa.bookstore.domain.model.Book

sealed class BookListState {
    data class Success(val books: List<Book>) : BookListState()
    data class Error(val exception: Exception) : BookListState()
    class Loading(value: Boolean = false) : BookListState()
}