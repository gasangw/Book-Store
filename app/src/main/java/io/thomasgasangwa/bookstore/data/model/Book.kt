package io.thomasgasangwa.bookstore.data.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val cover: String?,
    val genre: String,
    val description: String,
)
