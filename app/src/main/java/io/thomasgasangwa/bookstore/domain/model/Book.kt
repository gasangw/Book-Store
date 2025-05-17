package io.thomasgasangwa.bookstore.domain.model

data class Book(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val description: String,
    val pages: Int,
    val cover: String?,
    val likes: Int?,
)
