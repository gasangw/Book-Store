package io.thomasgasangwa.bookstore.presentation.add_book

sealed class AddBookUiState {
    data class Error(val exception: Exception) : AddBookUiState()
    class Loading(value: Boolean = false) : AddBookUiState()
}