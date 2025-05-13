package io.thomasgasangwa.bookstore.presentation.viewmodel.booklist_viewmodel

import io.thomasgasangwa.bookstore.data.local.entity.Book


sealed class BookListState {
    data class Success(val books: List<Book>) : BookListState()
    data class Error(val exception: Exception) : BookListState()
    class Loading(val message: String) : BookListState()
}

