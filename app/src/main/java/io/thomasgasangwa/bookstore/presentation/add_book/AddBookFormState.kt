package io.thomasgasangwa.bookstore.presentation.add_book

data class AddBookFormState(
    val title: String = "",
    val description: String = "",
    val releaseDate: String = "",
    val pages: Int = 0,
    val cover: String = "",
    val likes: Int = 0
)