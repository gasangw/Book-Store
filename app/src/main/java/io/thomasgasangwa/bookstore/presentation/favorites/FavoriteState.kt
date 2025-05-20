package io.thomasgasangwa.bookstore.presentation.favorites

import io.thomasgasangwa.bookstore.domain.model.Book

sealed class FavoriteBookState {
    data class Success(val books: List<Book>) : FavoriteBookState()
    data class Error(val exception: Exception) : FavoriteBookState()
    class Loading(value: Boolean = false) : FavoriteBookState()
}