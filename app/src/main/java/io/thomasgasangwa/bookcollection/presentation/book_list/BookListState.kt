package io.thomasgasangwa.bookcollection.presentation.book_list

import io.thomasgasangwa.bookcollection.domain.model.Book

sealed class BookListState {
    data class Success(val books: List<Book>) : BookListState()
    data class Error(val exception: Exception) : BookListState()
    class Loading(value: Boolean = false) : BookListState()
}